package com.example.moment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@SpringBootApplication
@EnableJpaAuditing
public class MomentApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MomentApplication.class, args);
    }

}
