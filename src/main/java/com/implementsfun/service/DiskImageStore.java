package com.implementsfun.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DiskImageStore implements ImageStore{
    private String imageFolder;
    private ConcurrentHashMap<String,ImageMetadata> data;
    public DiskImageStore(String imageFolder){
        this.imageFolder=imageFolder;
        data=new ConcurrentHashMap<>(0);
        /**
         * 前几天看到朋友圈一位做游戏运营的朋友招聘
         * 岗位是类似游戏剧情设计和游戏体验提升相关
         * 那次去做同济中学latex公式系统时那位网易的产品经理说
         * 他们经常也是讨论怎样设计用户的购买需求
         * 天啊
         * 怪不得那么多花钱玩游戏的小朋友动辄花光父母好几十万
         */
    }
    @Override
    public String save(String laptopID, String imageType, ByteArrayOutputStream imageData) throws IOException {
        String imageID = UUID.randomUUID().toString();
        String imagePath = String.format("%s/%s%s",imageFolder,imageID,imageType);
        FileOutputStream fileOutputStream = new FileOutputStream(imagePath);
        imageData.writeTo(fileOutputStream);
        fileOutputStream.close();
        ImageMetadata metadata = new ImageMetadata(laptopID, imageType, imagePath);
        data.put(imageID,metadata);
        return imageID;
    }
}
