package com.example.moment.repository;

import com.example.moment.entity.App;
import com.example.moment.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByItemId(Long id);
}
