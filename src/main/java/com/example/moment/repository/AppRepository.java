package com.example.moment.repository;

import com.example.moment.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppRepository extends JpaRepository<App, Long> {
    Optional<App> findByClaim(String claim);

    Optional<App> findByDid(String did);
}