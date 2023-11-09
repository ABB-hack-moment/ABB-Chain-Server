package com.example.moment.service;

import com.example.moment.dto.AccountDto;
import com.example.moment.dto.ReqItemDto;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

public interface DidService {
    AccountDto createAccount(HashMap reqRegistAccountMap);

    HashMap issueVP(HashMap reqIssueVPMap);

    Boolean verifyVP(String didToken, ReqItemDto reqItemDto);
}
