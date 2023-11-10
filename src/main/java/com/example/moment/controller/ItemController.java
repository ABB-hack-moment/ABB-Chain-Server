package com.example.moment.controller;


import com.example.moment.dto.Message;
import com.example.moment.handler.StatusCode;
import com.example.moment.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/")
    public Boolean getItemStatus(@RequestBody HashMap<String, Long> itemMap) {
        return itemService.getItemStatus(itemMap.get("itemId"));
    }

}
