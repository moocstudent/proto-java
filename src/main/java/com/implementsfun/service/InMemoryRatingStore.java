package com.implementsfun.service;

import com.implementsfun.entity.Rating;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRatingStore implements RatingStore{
    private ConcurrentHashMap<String,Rating> data;
    public InMemoryRatingStore(){
        data = new ConcurrentHashMap<>();
    }
    @Override
    public Rating add(String laptopID, double score) {
        return data.merge(laptopID,new Rating(1,score),Rating::add);
    }
}
