package com.example.moment.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name="Item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", unique = true, nullable = false)
    private Long itemId;

    @Column(name = "status")
    private Boolean status;

    @Builder
    public Item(Long itemId, Boolean status) {
        this.itemId = itemId;
        this.status = status;
    }

    public void updateStatus(Boolean status) {
        this.status = status;
    }
}
