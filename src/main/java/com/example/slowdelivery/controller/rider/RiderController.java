package com.example.slowdelivery.controller.rider;

import com.example.slowdelivery.dto.seller.SignUpRequest;
import com.example.slowdelivery.service.rider.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/rider/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest request) {

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        riderService.signUp(request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/rider/{riderId}/onoff")
    public ResponseEntity<Void> changeOnAndOff(@PathVariable Long riderId) {

        riderService.setOnAndOff(riderId);
        return ResponseEntity.noContent().build();
    }

    /**
     * TODO
     * 출퇴근 상태 변경
     * 배달요청중인 주문 목록 보기
     * 배차 신청
     * 배차 완료처리
     */
}
