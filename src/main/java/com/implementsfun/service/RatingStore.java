package com.implementsfun.service;

import com.implementsfun.entity.Rating;

/**
 * 在一些面试中有人经常问起接口和抽象类点区别
 * 其实我感觉这个区别就很明显了
 */
public interface RatingStore {
    Rating add(String laptopID,double score);
}
