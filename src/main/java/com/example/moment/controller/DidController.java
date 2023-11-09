package com.example.moment.controller;

import com.example.moment.dto.Message;
import com.example.moment.dto.ReqItemDto;
import com.example.moment.handler.StatusCode;
import com.example.moment.service.DidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/did")
public class DidController {
    private final DidService didService;

    @PostMapping("/registration")
    public ResponseEntity<Message> registAccount(@RequestBody HashMap<String, String> reqRegistAccountMap) {
        System.out.println("registAccountController IN");
        return ResponseEntity.ok(new Message(StatusCode.OK, didService.createAccount(reqRegistAccountMap)));
    }

    @PostMapping("/issue")
    public ResponseEntity<Message> issueVP(@RequestBody HashMap<String, String> reqIssueVPMap) {
        System.out.println("issueVP IN");
        return ResponseEntity.ok(new Message(StatusCode.OK, didService.issueVP(reqIssueVPMap)));
    }

    @PostMapping("/verification")
    public ResponseEntity<Message> verifyVP(@RequestHeader("Authorization") String didToken, @RequestBody ReqItemDto reqItemDto) {
        return ResponseEntity.ok(new Message(StatusCode.OK, didService.verifyVP(didToken.split(" ")[1], reqItemDto)));
    }
}
