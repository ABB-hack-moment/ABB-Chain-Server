package com.example.moment.entity;

import com.example.moment.baseTime.BaseTimeEntity;
import io.micrometer.core.annotation.Counted;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name="App")
public class App extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id", unique = true, nullable = false)
    private Long appId;

    @Column(name = "claim", unique = true, nullable = false)
    private String claim;

    @Column(name = "account_status")
    private Boolean accountStatus;

    @Column(name = "did")
    private String did;

    @Column(name="public_key")
    private String publicKey;

    @Builder
    public App(Long appId, String claim, Boolean accountStatus, String did, String publicKey) {
        this.appId = appId;
        this.claim = claim;
        this.accountStatus = accountStatus;
        this.did = did;
        this.publicKey = publicKey;
    }




    public void updateAccountStatus() {
        this.accountStatus = true;
    }
    public void registDidInfo(String did) {this.did = did;}
    public void registPublicKey(String publicKey) {this.publicKey = publicKey;}
}
