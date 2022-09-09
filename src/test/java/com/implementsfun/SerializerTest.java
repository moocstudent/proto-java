package com.implementsfun;

import com.implementsfun.protoj.LaptopMessage;
import com.implementsfun.serializer.Serializer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;

/**
 * 尝试努力找到protobuf的相关问题
 * 提交issue
 * 其他项目也可以呀
 * 类似Apache sharding好多提交的啊
 * 不过路还有很长啊
 * 你要改善一个事情一个物品一段代码
 * 必须是对这个事情对这个物品对这段代码
 * 了如指掌
 */
//@Slf4j
public class SerializerTest {

    @Test
    public void writeAndReadBinaryFile()throws IOException {
        String binaryFile = "laptop.bin";
        String jsonFile = "laptop.json";
        LaptopMessage.Laptop laptop = new Generator().initLaptop();

        Serializer serializer = new Serializer();
        serializer.writeBinaryFile(laptop,binaryFile);
        serializer.writeJSONFile(laptop,jsonFile);

        LaptopMessage.Laptop laptopFromFile = serializer.readBinaryFile(binaryFile);

        Assert.assertEquals(laptop,laptopFromFile);

        serializer.writeJSONFile(laptop,jsonFile);
        /**
         * 这个log不知道为什么一直没找到符号
         * gradle配置个lombok也是耍花一样的繁琐的
         * 查了两个CSDN解决的方式
         * 也空里看了下英国女王伊丽莎白二世谢幕的新闻
         */
//        log.info("laptop:{},laptopFromFile:{}",null,null);
    }
}
