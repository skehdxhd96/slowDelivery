package com.example.slowdelivery.domain.rider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkStatus {

    ON("ON", "출근"),
    OFF("OFF", "퇴근");

    private final String key;
    private final String value;
}
