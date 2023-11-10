package com.example.moment.service;

import com.example.moment.entity.Item;
import com.example.moment.handler.CustomException;
import com.example.moment.handler.StatusCode;
import com.example.moment.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    @Override
    public Boolean getItemStatus(Long itemId) {
        Item item = itemRepository.findByItemId(itemId).orElseThrow(() -> new CustomException(StatusCode.ITEM_NOT_FOUND));
        return item.getStatus();
    }
}
