package com.implementsfun.service;

import com.implementsfun.protoj.FilterMessage.*;
import com.implementsfun.protoj.LaptopMessage.*;
import com.implementsfun.protoj.LaptopServiceGrpc;
import com.implementsfun.protoj.LaptopServiceOuterClass.*;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.UUID;
import java.util.logging.Logger;

public class LaptopService extends LaptopServiceGrpc.LaptopServiceImplBase {

    private static final Logger logger =
            Logger.getLogger(LaptopService.class.getName());
    private LaptopStore store;

    public LaptopService(LaptopStore store){
        this.store = store;
    }

    @Override
    public void createLaptop(CreateLaptopRequest request,
                             StreamObserver<CreateLaptopResponse> responseObserver){
        Laptop laptop=request.getLaptop();
        String id = laptop.getId();
        logger.info("get a create-laptop request with ID:"+id);
        UUID uuid;
        if(id.isEmpty()){
            uuid=UUID.randomUUID();
        }else{
            try{
                uuid=UUID.fromString(id);
            }catch(IllegalArgumentException e){
                responseObserver.onError(
                        Status.INVALID_ARGUMENT
                                .withDescription(e.getMessage())
                                .asRuntimeException()
                );
                return;
            }
        }
        if(Context.current().isCancelled()){
            logger.info("request is cancelled");
            responseObserver.onError(
                    Status.CANCELLED
                            .withDescription("request is cancelled")
                            .asRuntimeException()
            );
        }
        Laptop other = laptop.toBuilder().setId(uuid.toString()).build();
        try{
            store.save(other);
        }catch(KeyAlreadyExistsException e){
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }catch(Exception e){
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        }
        CreateLaptopResponse response =
                CreateLaptopResponse.newBuilder().setId(other.getId()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

        logger.info("save laptop with ID: "+other.getId());
        /**
         * 早上起来到现在刚吃饭14：00
         * 因为快过中秋节了
         * 晚上订了星伦多请朋友（小战友）吃饭
         * 又请回来了
         * 看完人事睡觉到猫咪朋友圈
         * 再继续劳作吧
         *
         * 因为这个印度youtuber写到java代码方法名和类型都是大写开头
         * 我这个版本已经改掉了
         * 不然太麻烦了
         * 比如上面CreateLaptopResponse和createLaptop方法是区分到
         */
    }

    public void searchLaptop(SearchLaptopRequest request,
                             StreamObserver<SearchLaptopResponse> responseObserver){
        Filter filter = request.getFilter();
        logger.info("got a search-laptop request with filter:\t "+filter);
        store.search(Context.current(), filter, laptop -> {
            logger.info("found laptop with ID: "+laptop.getId());
            SearchLaptopResponse response=
                    SearchLaptopResponse.newBuilder().setLaptop(laptop).build();
            responseObserver.onNext(response);
        });
        //to tell client there won't be any more responses
        responseObserver.onCompleted();
        logger.info("search laptop completed");
    }

}
