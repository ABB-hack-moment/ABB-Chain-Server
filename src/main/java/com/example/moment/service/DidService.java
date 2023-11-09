package com.example.moment.service;

import com.example.moment.dto.AccountDto;

import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

public interface DidService {
    AccountDto createAccount(HashMap reqRegistAccountMap);
    void registPublicKey(HashMap reqRegistKeyMap);

    Boolean verifySignedData(HashMap verifyDataMap) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException, SignatureException;


    HashMap issueVP(HashMap reqIssueVPMap);

    Boolean verifyVP(String didToken);
}
