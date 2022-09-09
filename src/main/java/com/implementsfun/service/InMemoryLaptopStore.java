package com.implementsfun.service;

import com.implementsfun.protoj.LaptopMessage.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryLaptopStore implements LaptopStore {
    private ConcurrentHashMap<String,Laptop> data;
    public InMemoryLaptopStore(){
        data=new ConcurrentHashMap<>(0);
    }
    @Override
    public void save(Laptop laptop) throws KeyAlreadyExistsException {
        if(data.containsKey(laptop.getId())){
            throw new KeyAlreadyExistsException("laptop ID already exists");
        }
        Laptop other = laptop.toBuilder().build();
        data.put(other.getId(),other);
    }

    @Override
    public Laptop find(String id) {
        if(!data.containsKey(id)){
            return null;
        }
        return data.get(id).toBuilder().build();
    }
}
