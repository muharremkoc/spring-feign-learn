package com.example.itemservice.service;

import com.example.itemservice.client.ApiFeignClient;
import com.example.itemservice.domain.ItemDto;
import com.example.itemservice.domain.User;
import com.example.itemservice.model.Item;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class ItemServiceImpl implements ItemService{

    final ApiFeignClient apiFeignClient;

    public ItemServiceImpl(ApiFeignClient apiFeignClient) {
        this.apiFeignClient = apiFeignClient;
    }

    @Override
    @CircuitBreaker(name ="item-service",fallbackMethod = "orderFallBack")
    public Item create(ItemDto itemDto,Integer userId) {
        Item item = new Item(itemDto.getId(),itemDto.getItemName());
        item.setUser(getUser(userId));
        return item;
    }

    @Override
    public User getUser(Integer id) {
        return apiFeignClient.getUser(id);
    }

    public Item orderFallBack(Exception e) throws NotFoundException {

        String errorMessage=String.format(" %s \n%s","User Service downed",e.getMessage());

        throw new NotFoundException(errorMessage);

    }
}
