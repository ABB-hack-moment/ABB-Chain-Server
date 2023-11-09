package com.example.moment.service;

import com.example.moment.dto.AccountDto;
import com.example.moment.dto.ReqIssueDto;
import com.example.moment.entity.App;
import com.example.moment.handler.CustomException;
import com.example.moment.handler.StatusCode;
import com.example.moment.repository.AppRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class DidServiceImpl implements DidService {

    @Value("${dgChain.token}") private String adminToken;
    @Value("${dgChain.chain}") private String chainName;
    @Value("${dgChain.templateId}") private String templateID;
    @Value("${dgChain.didOrigin}") private String semiURI;

    private final RestTemplate restTemplate;
    private final AppRepository appRepository;

    @Override
    @Transactional
    public AccountDto createAccount(HashMap reqRegistAccountMap) {
        String createAccountURI = semiURI + "create_account";

        vcVerifier(reqRegistAccountMap.get("value").toString());

        HashMap result = (HashMap) restTemplate.postForObject(createAccountURI, createAccountBody(), HashMap.class).get("data");
        AccountDto accountDto = extractAccountDto(result);

        appRepository.findByClaim(reqRegistAccountMap.get("value").toString()).get().registDidInfo(accountDto.getDid());

        return accountDto;
    }

    @Override
    public void registPublicKey(HashMap reqRegistKeyMap) {
        App app = appRepository.findByDid(reqRegistKeyMap.get("did").toString()).orElseThrow(() ->  new CustomException(StatusCode.NON_EXIST_DID));
        app.registPublicKey(reqRegistKeyMap.get("publicKey").toString());
    }

    @Override
    public Boolean verifySignedData(HashMap verifyDataMap) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
        String encryptedData = verifyDataMap.get("signedData").toString();
        App app = appRepository.findByDid(verifyDataMap.get("did").toString()).orElseThrow(() -> new CustomException(StatusCode.NON_EXIST_DID));
//        System.out.println(app);
//        System.out.println("db publicKey: " + publicKey);
//        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
//        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
//        System.out.println("spect public Key: " + publicKeySpec);
//        KeyFactory keyFactory = KeyFactory.getInstance("EC");
//        PublicKey targetPublicKey = keyFactory.generatePublic(publicKeySpec);
//        System.out.println("target public key: " + targetPublicKey);
//        Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA");
//        ecdsaVerify.initVerify(targetPublicKey);
//        ecdsaVerify.update(encryptedData.getBytes("UTF-8"));
//
//        boolean result = ecdsaVerify.verify(publicKeyBytes);
//        System.out.println("result: " + result);
        return false;
    }

    @Override
    public HashMap issueVP(HashMap reqIssueVPMap) {
        String issueVPURI = semiURI + "issue";
        ReqIssueDto reqIssueDto = ReqIssueDto.builder()
                .token(adminToken)
                .chain(chainName)
                .did(reqIssueVPMap.get("did").toString())
                .template_id(templateID)
                .subject(extractReqRegistAccountDto(reqIssueVPMap))
                .validfrom(makeTimeFormat(0))
                .validuntil(makeTimeFormat(20))
                .build();
        HashMap result = restTemplate.postForObject(issueVPURI, reqIssueDto, HashMap.class);

        HashMap<String, String> jwt = new HashMap<>();
        jwt.put("jwt", ((HashMap) result.get("data")).get("jwt").toString());

        return jwt;
    }

    @Override
    public Boolean verifyVP(String didToken) {
        final String targetDid = tokenDecoder(didToken).split(",")[3].substring(6).split("\"")[1];

        String verifyVPURI = semiURI + "verification";
        HashMap result = restTemplate.postForObject(verifyVPURI, createVerifyVPBody(didToken), HashMap.class);

        String verifiedDid = ((HashMap) result.get("data")).get("aud").toString();
        System.out.println(targetDid);
        System.out.println(verifiedDid);
        if (targetDid.equals(verifiedDid)) return true;

        return false;
    }

    private String tokenDecoder(String didToken) {
        String[] parts = didToken.split("\\.");
        return new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
    }

    private void vcVerifier(String vc) {
        App app = appRepository.findByClaim(vc).orElseThrow(() -> new CustomException(StatusCode.DISABLED_VC));
        if (app.getAccountStatus()) throw new CustomException(StatusCode.ALREADY_EXISTS_VC);
        app.updateAccountStatus();

    }

    private HashMap createAccountBody() {
        HashMap<String, String> createAccountBody = new HashMap<>();
        createAccountBody.put("token", adminToken);
        createAccountBody.put("chain", chainName);
        return createAccountBody;
    }

    private HashMap createVerifyVPBody(String didJwt) {
        HashMap<String, String> createVerifyVPBody = new HashMap<>();
        createVerifyVPBody.put("token", adminToken);
        createVerifyVPBody.put("chain", chainName);
        createVerifyVPBody.put("template_id", templateID);
        createVerifyVPBody.put("jwt", didJwt);
        return createVerifyVPBody;
    }

    private AccountDto extractAccountDto(HashMap result) {
        String did =result.get("did").toString();
        HashMap wallet = (HashMap) result.get("wallet");
        return AccountDto.builder()
                .did(did)
                .wallet(wallet)
                .build();
    }

    private String makeTimeFormat(int dayDiff) {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(dayDiff);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        ZonedDateTime zonedDateTime = timestamp.toInstant().atZone(ZoneId.of("Z"));
        String formattedDate = zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);

        return formattedDate;
    }
    private HashMap extractReqRegistAccountDto(HashMap reqIssueVPDto) {
        HashMap<String, String> subject = new HashMap<>();
        subject.put("key", reqIssueVPDto.get("key").toString());
        subject.put("value", reqIssueVPDto.get("value").toString());
        return subject;
    }
}
