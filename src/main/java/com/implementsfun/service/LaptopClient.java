package com.implementsfun.service;

import com.google.protobuf.ByteString;
import com.implementsfun.protoj.FilterMessage.Filter;
import com.implementsfun.protoj.LaptopMessage.Laptop;
import com.implementsfun.protoj.LaptopServiceGrpc;
import com.implementsfun.protoj.LaptopServiceGrpc.LaptopServiceBlockingStub;
import com.implementsfun.protoj.LaptopServiceGrpc.LaptopServiceStub;
import com.implementsfun.protoj.LaptopServiceOuterClass.*;
import com.implementsfun.protoj.MemoryMessage.Memory;
import com.implementsfun.util.Generator;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
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
//    private final LaptopServiceFutureStub futureStub;
    private final LaptopServiceStub asyncStub;

    public LaptopClient(String host,int port){
        channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext()
                .build();
        //阻塞信道stub之类的
        blockingStub = LaptopServiceGrpc.newBlockingStub(channel);
        //异步stub
        asyncStub = LaptopServiceGrpc.newStub(channel);
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

    /**
     * 测试创建laptop
     * @param client
     * @param generator
     */
    public static void testCreateLaptop(LaptopClient client,Generator generator){
        Laptop laptop = generator.initLaptop();
        client.createLaptop(laptop);
    }

    /**
     * 测试查询laptop
     * 不同的测试单独抽离出来 解耦
     * @param client
     * @param generator
     */
    public static void testSearchLaptop(LaptopClient client,Generator generator){
            for (int i = 0; i < 10; i++) {
                Laptop laptop = generator.initLaptop();
                client.createLaptop(laptop);
            }
            Memory minRam = Memory.newBuilder()
                    .setValue(8)
                    .setUnit(Memory.Unit.GIGABYTE)
                    .build();
            Filter filter = Filter.newBuilder()
                    .setMaxPriceUsd(3000)
                    .setMinCpuCores(4)
                    .setMinCpuGhz(2.5)
                    .setMinRam(minRam)
                    .build();
            client.searchLaptop(filter);
    }

    /**
     * 测试上传图片
     * @param client
     * @param generator
     * @throws InterruptedException
     */
    public static void testUploadImage(LaptopClient client,Generator generator) throws InterruptedException {
        Laptop laptop = generator.initLaptop();
        client.createLaptop(laptop);
        client.uploadImage(laptop.getId(),"tmp/laptop.png");
    }

    /**
     * 测试电脑性能得分
     * @param client
     * @param generator
     * @throws InterruptedException
     */
    public static void testRateLaptop(LaptopClient client,Generator generator) throws InterruptedException {
       int n=3;
       String[] laptopIDs = new String[n];
       for (int i =0;i<n;i++){
           Laptop laptop = generator.initLaptop();
           laptopIDs[i]  = laptop.getId();
           client.createLaptop(laptop);
       }

        Scanner scanner = new Scanner(System.in);
        while(true){
           /**
            * 204斤，体检发现体重居然超过200
            * 当兵之后体重有时飙升到这个数字
            * 不过多加运动吧 会好起来到
            */
           logger.info("rate laptop {y/n} ?");
           String answer = scanner.nextLine();
           if(answer.toLowerCase().trim().equals("n")){
               break;
           }

           double[] scores = new double[n];
           for(int i=0;i<n;i++){
               scores[i] = generator.initLaptopScore();
           }

           client.rateLaptop(laptopIDs,scores);
       }

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
        try {
            testRateLaptop(client,generator);
        } finally {
            client.shutdown();
        }

    }
    /**
     * 今早没有吃饭，快到中午了，
     * 天气比较热，这会儿想订个餐算了
     * 枕头昨天放了本数学之美
     * 不知道什么时候能看完
     */
    private void searchLaptop(Filter filter){
        logger.info("search started");
        SearchLaptopRequest request =
                SearchLaptopRequest.newBuilder().setFilter(filter).build();
        try{
            Iterator<SearchLaptopResponse> responseIterator =
                    blockingStub
                            //这个方法是设定允许服务端和客户端本次request限时
                            //超过这个时间，server端即使还有数据则客户端不再接收
//                            .withDeadlineAfter(5,TimeUnit.SECONDS)
//                            .withCallCredentials()
                            .searchLaptop(request);

            while(responseIterator.hasNext()){
                SearchLaptopResponse response = responseIterator.next();
                Laptop laptop = response.getLaptop();
                logger.info("- found:"+laptop.getId());
            }
        }catch (Exception e){
            logger.log(Level.SEVERE,"request failed: "+e.getMessage());
            return;
        }
        logger.info("search completed");
    }

    public void uploadImage(String laptopID,String imagePath) throws InterruptedException {
        /**
         * CountDownLatch即来将线程进行统一结束的处理
         * 具体在CountDownLatch源码注释非常好理解
         * 为什么这里用
         * 因为多个client请求后
         * 服务端上传会在不同线程执行
         * 但为了等全部请求线程结束掉该streaming请求
         * 类似源码注释里startSignal
         */
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<UploadImageRequest> requestObserver =
                asyncStub.withDeadlineAfter(5, TimeUnit.SECONDS)
                        .uploadImage(new StreamObserver<UploadImageResponse>() {
                            @Override
                            public void onNext(UploadImageResponse response) {
                                logger.info("receive response:\n"+response);
                            }

                            @Override
                            public void onError(Throwable t) {
                                logger.log(Level.SEVERE,"upload failed: "+t.getMessage());
                                finishLatch.countDown();
                            }

                            @Override
                            public void onCompleted() {
                                logger.info("image uploaded");
                                /**
                                 * 这个博主除了Java方法开头大写不规范外
                                 * 其他使用但技术点还挺全的哈
                                 */
                                finishLatch.countDown();
                            }
                        });
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(imagePath);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE,"cannot read image file: "+e.getMessage());
            return;
        }

        String imageType = imagePath.substring(imagePath.lastIndexOf("."));
        ImageInfo info = ImageInfo.newBuilder().setLaptopId(laptopID).setImageType(imageType).build();
        UploadImageRequest request = UploadImageRequest.newBuilder().setInfo(info).build();

        try {
            requestObserver.onNext(request);
            logger.info("sent image info:\n "+info);
            byte[] buffer = new byte[1024];
            while(true){
                int n = fileInputStream.read(buffer);
                if(n<=0){
                    break;
                }
                if (finishLatch.getCount() == 0) {
                    return;
                }
                request=UploadImageRequest.newBuilder()
                        .setChunkData(ByteString.copyFrom(buffer,0,n))
                        .build();
                requestObserver.onNext(request);
                logger.info("sent image chunk with size: "+n);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE,"unexpected error: "+e.getMessage());
            requestObserver.onError(e);
            return;
        }

        requestObserver.onCompleted();
        /**
         * 如果latch 1分钟后依旧没有传输完，那么结束
         */
        if(!finishLatch.await(1,TimeUnit.MINUTES)){
            logger.warning("request cannot finish within 1 minute");
        }
    }

    public void rateLaptop(String[] laptopIds,double[] scores) throws InterruptedException {
        /**
         * 博主的这里方法没有贴 得看youtube
         */
        CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<RateLaptopRequest> requestObserver = asyncStub.withDeadlineAfter(5, TimeUnit.SECONDS)
                .rateLaptop(new StreamObserver<RateLaptopResponse>() {
                    @Override
                    public void onNext(RateLaptopResponse response) {
                        logger.info("laptop rated: id = " + response.getLaptopId() +
                                ", count = " + response.getRatedCount() +
                                ", average = " + response.getAverageScore());
                    }

                    /**
                     * 所以说 com.google.inject包下的接口具体都是什么作用
                     * @param t
                     */

                    @Override
                    public void onError(Throwable t) {
                        logger.log(Level.SEVERE, "rate laptop failed: " + t.getMessage());
                        finishLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        logger.info("rate laptop completed");
                        finishLatch.countDown();
                    }
                });
        int n = laptopIds.length;
        logger.info("the laptopIds length is "+n);
        try {
            for(int i=0;i<n;i++){
                RateLaptopRequest request = RateLaptopRequest.newBuilder()
                        .setLaptopId(laptopIds[i])
                        .setScore(scores[i])
                        .build();
                requestObserver.onNext(request);
                logger.info("sent rate-laptop request: id = "+request.getLaptopId()+", score = "+request.getScore());
            }
        } catch (Exception e) {
           logger.log(Level.SEVERE,"unexcepted error: "+e.getMessage());
           requestObserver.onError(e);
           return;
        }

        requestObserver.onCompleted();
        if(!finishLatch.await(1,TimeUnit.MINUTES)){
            logger.warning("request cannot finish within 1 minutes");
        }
    }
}
