package com.example.moment.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@Getter
@RequiredArgsConstructor
public class AccountDto {
    private String did;
    private HashMap<String, String> wallet;

    @Builder
    public AccountDto(String did, HashMap<String, String> wallet) {
        this.did = did;
        this.wallet = wallet;
    }
}
