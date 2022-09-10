package com.implementsfun.service;

import com.implementsfun.protoj.FilterMessage.*;
import com.implementsfun.protoj.LaptopMessage.*;
import com.implementsfun.protoj.MemoryMessage.*;
import io.grpc.Context;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * store实现类 表现为用map做存储
 */
public class InMemoryLaptopStore implements LaptopStore {

    private static final Logger logger =
            Logger.getLogger(InMemoryLaptopStore.class.getName());
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

    @Override
    public void search(Context ctx, Filter filter, LaptopStream stream) {
        for(Map.Entry<String,Laptop> entry:data.entrySet()){
            /**
             * 如果当前grpc context上下文停止了接收，也即在client设置
             * deadline如.withDeadlineAfter(5,TimeUnit.SECONDS)
             * 说明这个客户端请求将在5秒后停止接收server响应
             * 那么判定ctx.isCancelled会立即终止服务端的查询
             */
//            if(ctx.isCancelled()){
//                logger.info("context is cancelled");
//            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Laptop laptop = entry.getValue();
            if(isQualified(laptop,filter)){
                stream.send(laptop.toBuilder().build());
            }
        }
    }

    private boolean isQualified(Laptop laptop,Filter filter) {
        if(laptop.getPriceUsd()>filter.getMaxPriceUsd()){
            return false;
        }
        if(laptop.getCpu().getNumberCores()<filter.getMinCpuCores()){
            return false;
        }
        if(laptop.getCpu().getMinGhz()<filter.getMinCpuGhz()){
            return false;
        }
        if(toBit(laptop.getRam())<toBit(filter.getMinRam())){
            return false;
        }
        return true;
    }

    /**
     * bit:比特 计算机内运算最小单位
     * byte:字节 1字节=8比特 2的3次幂 2^3bit
     * kilobyte:千字节 1千字节=1024byte = 2^10byte = 2^13bit
     * megabyte:百万字节(兆字节) = 2^23bit
     * gigabyte:千兆字节 = 2^33bit
     * terabyte:万亿字节(太字节) = 2^43bit
     * @param memory
     * @return
     */
    private long toBit(Memory memory) {
        long value = memory.getValue();
        switch (memory.getUnit()){
            case BIT:return value;
            case BYTE:return value<<3;
            case KILOBYTE:return value<<13;
            case MEGABYTE:return value<<23;
            case GIGABYTE:return value<<33;
            case TERABYTE:return value<<43;
            default:return 0;
        }
    }
}
