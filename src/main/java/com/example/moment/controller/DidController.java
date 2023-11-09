package com.example.moment.controller;

import com.example.moment.dto.Message;
import com.example.moment.handler.StatusCode;
import com.example.moment.service.DidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/did")
public class DidController {
    private final DidService didService;

    @PostMapping("/registration")
    public ResponseEntity<Message> registAccount(@RequestBody HashMap<String, String> reqRegistAccountMap) {
        return ResponseEntity.ok(new Message(StatusCode.OK, didService.createAccount(reqRegistAccountMap)));
    }

    @PostMapping("/key_registration")
    public ResponseEntity<Message> registKey(@RequestBody HashMap<String, String> reqRegistKeyMap) {
        didService.registPublicKey(reqRegistKeyMap);
        return ResponseEntity.ok(new Message<>(StatusCode.OK));
    }

    @PostMapping("/ecc_verification")
    public ResponseEntity<Message> verifyWithECC(@RequestBody HashMap<String, String> verifyDataMap) {
        try {
            return ResponseEntity.ok(new Message(StatusCode.OK,didService.verifySignedData(verifyDataMap)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/issue")
    public ResponseEntity<Message> issueVP(@RequestBody HashMap<String, String> reqIssueVPMap) {
        return ResponseEntity.ok(new Message(StatusCode.OK, didService.issueVP(reqIssueVPMap)));
    }

    @PostMapping("/verification")
    public ResponseEntity<Message> verifyVp(@RequestHeader("Authorization") String didToken) {
        return ResponseEntity.ok(new Message(StatusCode.OK, didService.verifyVP(didToken.split(" ")[1])));
    }
}
