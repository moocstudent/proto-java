package com.implementsfun.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rating {
    private int count;
    private double sum;

    public static Rating add(Rating r1,Rating r2){
        return new Rating(r1.count+r2.count,r1.sum+r2.sum);
    }
}
