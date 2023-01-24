package com.example.itemservice.service;

import com.example.itemservice.domain.ItemDto;
import com.example.itemservice.domain.User;
import com.example.itemservice.model.Item;

public interface ItemService {

    Item create(ItemDto itemDto,Integer userId);

    User getUser(Integer id);
}
