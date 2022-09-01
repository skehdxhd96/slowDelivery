package com.example.slowdelivery.repository.cart;

import com.example.slowdelivery.domain.cart.Cart;
import com.example.slowdelivery.domain.cart.CartItem;
import com.example.slowdelivery.dto.cart.CartItemRequest;
import com.example.slowdelivery.exception.CartException;
import com.example.slowdelivery.exception.ErrorCode;
import io.lettuce.core.RedisException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static com.example.slowdelivery.utils.RedisKeyFactory.*;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    /**
     * TODO : Redis executePipeLine을 이용해 atomic 보장하기
     */
    private final RedisTemplate<String, Object> redisTemplate;

    private String generateCartKey(Long userId) {
        return generateCartId(userId);
    }

    public void addProductToCart(Long userId, CartItem item) {
        Cart myCart  = (Cart) redisTemplate.opsForHash().get(generateCartId(userId), userId);
        myCart.addCartItem(item);

        redisTemplate.opsForHash().put(generateCartKey(userId), userId, myCart);
    }

    public Cart getCart(Long userId) {
        Cart myCart = (Cart) redisTemplate.opsForHash().get(generateCartKey(userId), userId);
        if(myCart == null) throw new RedisException("장바구니 보관기간이 만료되었습니다.");

        return myCart;
    }

    public void deleteProduct(Long userId, Long productId) {
        Cart myCart = (Cart) redisTemplate.opsForHash().get(generateCartKey(userId), userId);
        myCart.deleteCartItem(productId);

        redisTemplate.opsForHash().put(generateCartKey(userId), userId, myCart);
    }

    public Cart getCartListAndDelete(Long userId) {

        String key = generateCartKey(userId);

        if(!redisTemplate.hasKey(key)) throw new RedisException("장바구니 보관기간이 만료되었습니다.");
        List<Object> cart = redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {

                try {
                    operations.watch(key);
                    operations.multi();
                    operations.opsForHash().get(key, userId);
                    operations.delete(key);
                    return operations.exec();
                } catch (Exception e) {
                    operations.discard();
                    throw new RedisException("장바구니에서 상품을 불러올 수 없습니다.");
                }
            }
        });

        return (Cart) cart.get(0);
    }

    public void RollbackCartOnOrder(Long userId, Cart cart) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if(status == STATUS_ROLLED_BACK) redisTemplate.opsForHash().put(generateCartKey(userId), userId, cart);
            }
        });
    }

    public void hasKeyOrCreateCart(Long userId) {
        // NullPointerException조심
        if(!redisTemplate.hasKey(generateCartId(userId))) {
            redisTemplate.opsForHash().put(generateCartKey(userId), userId, Cart.of(String.valueOf(userId)));
        }
    }
}
