package com.example.moment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.security.auth.Subject;
import java.time.ZonedDateTime;
import java.util.HashMap;

@Getter
@RequiredArgsConstructor
public class ReqIssueDto {
    private String token;
    private String chain;
    private String did;
    private String template_id;
    private HashMap subject;
    private String validfrom;
    private String validuntil;

    @Builder
    public ReqIssueDto(String token, String chain, String did, String template_id, HashMap subject, String validfrom, String validuntil) {
        this.token = token;
        this.chain = chain;
        this.did = did;
        this.template_id = template_id;
        this.subject = subject;
        this.validfrom = validfrom;
        this.validuntil = validuntil;
    }
}
