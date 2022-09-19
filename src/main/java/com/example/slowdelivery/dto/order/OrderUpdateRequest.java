package com.example.slowdelivery.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderUpdateRequest {

    @NotBlank
    private LocalDateTime reservationTime;
}
