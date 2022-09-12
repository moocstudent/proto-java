package com.implementsfun.service;

import com.google.protobuf.ByteString;
import com.implementsfun.protoj.FilterMessage.*;
import com.implementsfun.protoj.LaptopMessage.*;
import com.implementsfun.protoj.LaptopServiceGrpc;
import com.implementsfun.protoj.LaptopServiceOuterClass.*;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

public class LaptopService extends LaptopServiceGrpc.LaptopServiceImplBase {

    private static final Logger logger =
            Logger.getLogger(LaptopService.class.getName());
    private LaptopStore laptopStore;
    private ImageStore imageStore;

    public LaptopService(LaptopStore laptopStore){
        this.laptopStore = laptopStore;
    }

    public LaptopService(LaptopStore laptopStore,ImageStore imageStore){
        this.laptopStore = laptopStore;
        this.imageStore = imageStore;
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
            laptopStore.save(other);
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

    @Override
    public void searchLaptop(SearchLaptopRequest request,
                             StreamObserver<SearchLaptopResponse> responseObserver){
        Filter filter = request.getFilter();
        logger.info("got a search-laptop request with filter:\t "+filter);
        laptopStore.search(Context.current(), filter, laptop -> {
            logger.info("found laptop with ID: "+laptop.getId());
            SearchLaptopResponse response=
                    SearchLaptopResponse.newBuilder().setLaptop(laptop).build();
            responseObserver.onNext(response);
        });
        //to tell client there won't be any more responses
        responseObserver.onCompleted();
        logger.info("search laptop completed");
    }

    @Override
    public StreamObserver<UploadImageRequest> uploadImage(StreamObserver<UploadImageResponse> responseObserver){
        return new StreamObserver<UploadImageRequest>() {
            //可以上传图片大小限制byte
            private static final int MAX_IMAGE_SIZE = 2<<20;
            private String laptopID;
            private String imageType;
            private ByteArrayOutputStream imageData;
            @Override
            public void onNext(UploadImageRequest request) {
                if(request.getDataCase()==UploadImageRequest.DataCase.INFO){
                    ImageInfo info = request.getInfo();
                    /**
                     * 经年累月的积累
                     * 就算是一点点
                     * 那也可能积累为指数式增长
                     * 但是不积累
                     * 就会随着时间的长河遗忘
                     */
                    logger.info("receive image info:\n"+info);
                    laptopID = info.getLaptopId();
                    imageType = info.getImageType();
                    imageData = new ByteArrayOutputStream();

                    Laptop found = laptopStore.find(laptopID);
                    if(found==null){
                        responseObserver.onError(
                                Status.NOT_FOUND
                                        .withDescription("laptop not found by ID: "+laptopID)
                                        .asRuntimeException()
                        );
                    }
                    return;
                }
                ByteString chunkData = request.getChunkData();
                logger.info("receive image chunk with size: "+chunkData.size());
                if(imageData==null){
                    logger.info("image info wasn't sent before");
                    responseObserver.onError(
                            Status.INVALID_ARGUMENT
                                    .withDescription("image info wasn't sent before")
                                    .asRuntimeException()
                    );
                    return;
                }

                int size = imageData.size() + chunkData.size();
                if(size>MAX_IMAGE_SIZE){
                    logger.info("image is too large: "+size);
                    responseObserver.onError(
                            Status.INVALID_ARGUMENT
                                    .withDescription("image is too large: "+size)
                                    .asRuntimeException()
                    );
                    return;
                }
                logger.info("now image total size is: "+size);
                try {
                    chunkData.writeTo(imageData);
                }catch(IOException e){
                    responseObserver.onError(
                            Status.INTERNAL
                                    .withDescription("cannot write chunk data: "+e.getMessage())
                                    .asRuntimeException()
                    );
                    return;
                }
            }

            @Override
            public void onError(Throwable t) {
                logger.warning(t.getMessage());
            }

            @Override
            public void onCompleted() {
                String imageID= "";
                int imageSize = imageData.size();
                logger.info("image total size is: "+imageSize+" byte");
                try {
                    imageID = imageStore.save(laptopID,imageType,imageData);
                } catch (IOException e) {
                    responseObserver.onError(
                            Status.INTERNAL
                                    .withDescription("cannot save image to the store: "+e.getMessage())
                                    .asRuntimeException()
                    );
                }
                UploadImageResponse res = UploadImageResponse.newBuilder()
                        .setId(imageID)
                        .setSize(imageSize)
                        .build();
                responseObserver.onNext(res);
                responseObserver.onCompleted();

            }
        };
    }

}
