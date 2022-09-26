package com.example.slowdelivery.controller.rider;

import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.common.annotation.CustomerOnly;
import com.example.slowdelivery.common.annotation.RiderOnly;
import com.example.slowdelivery.dto.customer.CustomerRequest;
import com.example.slowdelivery.dto.order.OrderPartition;
import com.example.slowdelivery.dto.rider.RiderRequest;
import com.example.slowdelivery.dto.seller.SignUpRequest;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.service.order.OrderService;
import com.example.slowdelivery.service.rider.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/rider/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest request) {

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        riderService.signUp(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/rider/current-address")
    @CustomerOnly
    public ResponseEntity<Void> updateCurrentAddress(@CurrentUser UserPrincipal user, RiderRequest request) {
        riderService.updateRiderAddress(user.toRider(), request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/rider/{riderId}/onoff")
    public ResponseEntity<Void> changeOnAndOff(@PathVariable Long riderId) {

        riderService.setOnAndOff(riderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/rider/order-waiting-list")
    @RiderOnly
    public ResponseEntity<List<OrderPartition>> getOrderWaiting(@CurrentUser UserPrincipal user) {
        List<OrderPartition> orderWaitingList = orderService.getOrderWaitingList(user.toRider());
        return ResponseEntity.ok(orderWaitingList);
    }
    /**
     * TODO
     * 배차 신청 : 한 주문을 동시에 여러 라이더가 배차신청을 한다면 ? / 주문에 배정된 라이더를 표시해줘야할 것 같다면 ? / 느린배달 / 일반배달 따로 완료처리
     * 배차 완료처리 : 느린배달 / 일반배달 따로 완료처리
     * 내게(라이더) 할당된 주문정보 보기 : 주문에 배차신청할 주문과 라이더 짝지어줘야 함(1번과 동일)
     */
}
