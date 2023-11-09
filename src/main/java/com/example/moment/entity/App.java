package com.example.moment.entity;

import com.example.moment.baseTime.BaseTimeEntity;
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



    @Builder
    public App(Long appId, String claim, Boolean accountStatus) {
        this.appId = appId;
        this.claim = claim;
        this.accountStatus = accountStatus;
    }

    public void updateAccountStatus() {
        this.accountStatus = true;
    }
}
