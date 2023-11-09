package com.example.moment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReqItemDto {
    private Long itemId;
    private Boolean status;

    @Builder
    public ReqItemDto(Long itemId, Boolean status) {
        this.itemId = itemId;
        this.status = status;
    }
}
