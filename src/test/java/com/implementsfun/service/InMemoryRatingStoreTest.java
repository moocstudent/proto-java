package com.implementsfun.service;

import com.implementsfun.entity.Rating;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryRatingStoreTest {

    @Test
    public void add() throws InterruptedException {
        InMemoryRatingStore ratingStore = new InMemoryRatingStore();
        List<Callable<Rating>> tasks = new LinkedList<>();
        String laptopID = UUID.randomUUID().toString();
        double score =5;
        int n =10;
        for(int i=0;i<n;i++){
            tasks.add(()->ratingStore.add(laptopID,score));
        }
        Set<Integer> rateCount = new HashSet<>();
        Executors.newWorkStealingPool()
                .invokeAll(tasks)
                .stream()
                .forEach(future->{
                    try {
                        Rating rating = future.get();
                        assertEquals(rating.getSum(),rating.getCount()*score,1e-9);
                        rateCount.add(rating.getCount());
                    } catch (InterruptedException e) {
                       e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                });
        assertEquals(n,rateCount.size());
        for(int cnt=1;cnt<=n;cnt++){
            assertTrue(rateCount.contains(cnt));
        }
    }
}