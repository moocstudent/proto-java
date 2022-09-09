package com.implementsfun.service;

import com.implementsfun.Generator;
import com.implementsfun.protoj.LaptopMessage.*;
import com.implementsfun.protoj.LaptopServiceGrpc;
import com.implementsfun.protoj.LaptopServiceGrpc.*;
import com.implementsfun.protoj.LaptopServiceOuterClass.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * client 以及 client启动类
 */
public class LaptopClient {
    private static final Logger logger = Logger.getLogger(LaptopClient.class.getName());

    private final ManagedChannel channel;
    private final LaptopServiceBlockingStub blockingStub;

    public LaptopClient(String host,int port){
        channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext()
                .build();
        //阻塞信道stub之类的
        blockingStub = LaptopServiceGrpc.newBlockingStub(channel);
    }
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    public void createLaptop(Laptop laptop){
       CreateLaptopRequest request=CreateLaptopRequest.newBuilder().setLaptop(laptop).build();
       CreateLaptopResponse response=CreateLaptopResponse.getDefaultInstance();
       try{
           response=blockingStub.createLaptop(request);
       }catch (StatusRuntimeException e){
           if(e.getStatus().getCode()== Status.Code.ALREADY_EXISTS){
               logger.info("laptop ID already exists");
               return;
           }
           logger.log(Level.SEVERE,"request failed: "+e.getMessage());
           return;
       }catch (Exception e){
           logger.log(Level.SEVERE,"request failed: "+e.getMessage());
           return;
       }
       logger.info("laptop created with ID: "+response.getId());
    }

    public static void main(String[] args) throws InterruptedException {
        /**
         * 严格说来，0.0.0.0已经不是一个真正意义上的IP地址了。
         * 　　它表示的是这样一个集合：
         * 　　1、所有不清楚的主机和目的网络。这里的“不清楚”是指在本机的路由表里没有特定条目指明如何到达。
         * 　　2、对本机来说，它就是一个“收容所”，所有不认识的“三无”人员，一 律送进去。
         * 　　3、如果在网络设置中设置了缺省网关，那么Windows系统会自动产生一个目的地址为0.0.0.0的缺省路由。
         */
        LaptopClient client = new LaptopClient("0.0.0.0", 8080);
        Generator generator = new Generator();
        Laptop laptop = generator.initLaptop();
        try{
            client.createLaptop(laptop);
        }finally {
            client.shutdown();
        }

    }
}
