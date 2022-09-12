package com.implementsfun.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * server端
 */
public class LaptopServer {
    /**
     * 原有端部分博客在logger端对应class上有问题 都是service的
     * 本版本已经改掉了
     */
    private static final Logger logger =
            Logger.getLogger(LaptopServer.class.getName());
    private final int port;
    private final Server server;
    public LaptopServer(int port,LaptopStore store){
        this(ServerBuilder.forPort(port),port,store);
    }
    public LaptopServer(int port,LaptopStore store,ImageStore imageStore){
        this(ServerBuilder.forPort(port),port,store,imageStore);
    }

    public LaptopServer(ServerBuilder serverBuilder,int port,LaptopStore store){
        this.port=port;
        LaptopService laptopService = new LaptopService(store);
        server = serverBuilder.addService(laptopService).build();
    }

    public LaptopServer(ServerBuilder serverBuilder, int port, LaptopStore store, ImageStore imageStore) {
        this.port=port;
        LaptopService laptopService = new LaptopService(store,imageStore);
        server = serverBuilder.addService(laptopService).build();
    }

    public void start() throws IOException {
        server.start();
        logger.info("server started on port: "+port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("shut down gRPC server because JVM shuts down");
            try {
                LaptopServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            logger.info("server shut down");
        }));
    }

    public void stop() throws InterruptedException {
        if(server!=null){
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }
    private void blackUnitShutdown() throws InterruptedException {
        if(server!=null){
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        InMemoryLaptopStore laptopStore = new InMemoryLaptopStore();
        DiskImageStore imageStore = new DiskImageStore("img");
        LaptopServer server = new LaptopServer(8080,laptopStore,imageStore);
        server.start();
        server.blackUnitShutdown();
    }
}
