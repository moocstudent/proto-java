package com.implementsfun.protoj;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 **
 *laptop相关服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.49.0)",
    comments = "Source: laptop_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LaptopServiceGrpc {

  private LaptopServiceGrpc() {}

  public static final String SERVICE_NAME = "LaptopService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<LaptopServiceOuterClass.CreateLaptopRequest,
      LaptopServiceOuterClass.CreateLaptopResponse> getCreateLaptopMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "createLaptop",
      requestType = LaptopServiceOuterClass.CreateLaptopRequest.class,
      responseType = LaptopServiceOuterClass.CreateLaptopResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<LaptopServiceOuterClass.CreateLaptopRequest,
      LaptopServiceOuterClass.CreateLaptopResponse> getCreateLaptopMethod() {
    io.grpc.MethodDescriptor<LaptopServiceOuterClass.CreateLaptopRequest, LaptopServiceOuterClass.CreateLaptopResponse> getCreateLaptopMethod;
    if ((getCreateLaptopMethod = LaptopServiceGrpc.getCreateLaptopMethod) == null) {
      synchronized (LaptopServiceGrpc.class) {
        if ((getCreateLaptopMethod = LaptopServiceGrpc.getCreateLaptopMethod) == null) {
          LaptopServiceGrpc.getCreateLaptopMethod = getCreateLaptopMethod =
              io.grpc.MethodDescriptor.<LaptopServiceOuterClass.CreateLaptopRequest, LaptopServiceOuterClass.CreateLaptopResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "createLaptop"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  LaptopServiceOuterClass.CreateLaptopRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  LaptopServiceOuterClass.CreateLaptopResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LaptopServiceMethodDescriptorSupplier("createLaptop"))
              .build();
        }
      }
    }
    return getCreateLaptopMethod;
  }

  private static volatile io.grpc.MethodDescriptor<LaptopServiceOuterClass.SearchLaptopRequest,
      LaptopServiceOuterClass.SearchLaptopResponse> getSearchLaptopMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "searchLaptop",
      requestType = LaptopServiceOuterClass.SearchLaptopRequest.class,
      responseType = LaptopServiceOuterClass.SearchLaptopResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<LaptopServiceOuterClass.SearchLaptopRequest,
      LaptopServiceOuterClass.SearchLaptopResponse> getSearchLaptopMethod() {
    io.grpc.MethodDescriptor<LaptopServiceOuterClass.SearchLaptopRequest, LaptopServiceOuterClass.SearchLaptopResponse> getSearchLaptopMethod;
    if ((getSearchLaptopMethod = LaptopServiceGrpc.getSearchLaptopMethod) == null) {
      synchronized (LaptopServiceGrpc.class) {
        if ((getSearchLaptopMethod = LaptopServiceGrpc.getSearchLaptopMethod) == null) {
          LaptopServiceGrpc.getSearchLaptopMethod = getSearchLaptopMethod =
              io.grpc.MethodDescriptor.<LaptopServiceOuterClass.SearchLaptopRequest, LaptopServiceOuterClass.SearchLaptopResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "searchLaptop"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  LaptopServiceOuterClass.SearchLaptopRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  LaptopServiceOuterClass.SearchLaptopResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LaptopServiceMethodDescriptorSupplier("searchLaptop"))
              .build();
        }
      }
    }
    return getSearchLaptopMethod;
  }

  private static volatile io.grpc.MethodDescriptor<LaptopServiceOuterClass.UploadImageRequest,
      LaptopServiceOuterClass.UploadImageResponse> getUploadImageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "uploadImage",
      requestType = LaptopServiceOuterClass.UploadImageRequest.class,
      responseType = LaptopServiceOuterClass.UploadImageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<LaptopServiceOuterClass.UploadImageRequest,
      LaptopServiceOuterClass.UploadImageResponse> getUploadImageMethod() {
    io.grpc.MethodDescriptor<LaptopServiceOuterClass.UploadImageRequest, LaptopServiceOuterClass.UploadImageResponse> getUploadImageMethod;
    if ((getUploadImageMethod = LaptopServiceGrpc.getUploadImageMethod) == null) {
      synchronized (LaptopServiceGrpc.class) {
        if ((getUploadImageMethod = LaptopServiceGrpc.getUploadImageMethod) == null) {
          LaptopServiceGrpc.getUploadImageMethod = getUploadImageMethod =
              io.grpc.MethodDescriptor.<LaptopServiceOuterClass.UploadImageRequest, LaptopServiceOuterClass.UploadImageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "uploadImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  LaptopServiceOuterClass.UploadImageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  LaptopServiceOuterClass.UploadImageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LaptopServiceMethodDescriptorSupplier("uploadImage"))
              .build();
        }
      }
    }
    return getUploadImageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LaptopServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LaptopServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LaptopServiceStub>() {
        @Override
        public LaptopServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LaptopServiceStub(channel, callOptions);
        }
      };
    return LaptopServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LaptopServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LaptopServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LaptopServiceBlockingStub>() {
        @Override
        public LaptopServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LaptopServiceBlockingStub(channel, callOptions);
        }
      };
    return LaptopServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LaptopServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LaptopServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LaptopServiceFutureStub>() {
        @Override
        public LaptopServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LaptopServiceFutureStub(channel, callOptions);
        }
      };
    return LaptopServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   **
   *laptop相关服务
   * </pre>
   */
  public static abstract class LaptopServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     **
     *包括请求参数和返回参数
     * </pre>
     */
    public void createLaptop(LaptopServiceOuterClass.CreateLaptopRequest request,
                             io.grpc.stub.StreamObserver<LaptopServiceOuterClass.CreateLaptopResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateLaptopMethod(), responseObserver);
    }

    /**
     */
    public void searchLaptop(LaptopServiceOuterClass.SearchLaptopRequest request,
                             io.grpc.stub.StreamObserver<LaptopServiceOuterClass.SearchLaptopResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSearchLaptopMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<LaptopServiceOuterClass.UploadImageRequest> uploadImage(
        io.grpc.stub.StreamObserver<LaptopServiceOuterClass.UploadImageResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getUploadImageMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateLaptopMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                LaptopServiceOuterClass.CreateLaptopRequest,
                LaptopServiceOuterClass.CreateLaptopResponse>(
                  this, METHODID_CREATE_LAPTOP)))
          .addMethod(
            getSearchLaptopMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                LaptopServiceOuterClass.SearchLaptopRequest,
                LaptopServiceOuterClass.SearchLaptopResponse>(
                  this, METHODID_SEARCH_LAPTOP)))
          .addMethod(
            getUploadImageMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                LaptopServiceOuterClass.UploadImageRequest,
                LaptopServiceOuterClass.UploadImageResponse>(
                  this, METHODID_UPLOAD_IMAGE)))
          .build();
    }
  }

  /**
   * <pre>
   **
   *laptop相关服务
   * </pre>
   */
  public static final class LaptopServiceStub extends io.grpc.stub.AbstractAsyncStub<LaptopServiceStub> {
    private LaptopServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected LaptopServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LaptopServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     *包括请求参数和返回参数
     * </pre>
     */
    public void createLaptop(LaptopServiceOuterClass.CreateLaptopRequest request,
                             io.grpc.stub.StreamObserver<LaptopServiceOuterClass.CreateLaptopResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateLaptopMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchLaptop(LaptopServiceOuterClass.SearchLaptopRequest request,
                             io.grpc.stub.StreamObserver<LaptopServiceOuterClass.SearchLaptopResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getSearchLaptopMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<LaptopServiceOuterClass.UploadImageRequest> uploadImage(
        io.grpc.stub.StreamObserver<LaptopServiceOuterClass.UploadImageResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getUploadImageMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   **
   *laptop相关服务
   * </pre>
   */
  public static final class LaptopServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<LaptopServiceBlockingStub> {
    private LaptopServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected LaptopServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LaptopServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     *包括请求参数和返回参数
     * </pre>
     */
    public LaptopServiceOuterClass.CreateLaptopResponse createLaptop(LaptopServiceOuterClass.CreateLaptopRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateLaptopMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<LaptopServiceOuterClass.SearchLaptopResponse> searchLaptop(
        LaptopServiceOuterClass.SearchLaptopRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getSearchLaptopMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   **
   *laptop相关服务
   * </pre>
   */
  public static final class LaptopServiceFutureStub extends io.grpc.stub.AbstractFutureStub<LaptopServiceFutureStub> {
    private LaptopServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected LaptopServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LaptopServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     *包括请求参数和返回参数
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<LaptopServiceOuterClass.CreateLaptopResponse> createLaptop(
        LaptopServiceOuterClass.CreateLaptopRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateLaptopMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_LAPTOP = 0;
  private static final int METHODID_SEARCH_LAPTOP = 1;
  private static final int METHODID_UPLOAD_IMAGE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LaptopServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LaptopServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_LAPTOP:
          serviceImpl.createLaptop((LaptopServiceOuterClass.CreateLaptopRequest) request,
              (io.grpc.stub.StreamObserver<LaptopServiceOuterClass.CreateLaptopResponse>) responseObserver);
          break;
        case METHODID_SEARCH_LAPTOP:
          serviceImpl.searchLaptop((LaptopServiceOuterClass.SearchLaptopRequest) request,
              (io.grpc.stub.StreamObserver<LaptopServiceOuterClass.SearchLaptopResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPLOAD_IMAGE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.uploadImage(
              (io.grpc.stub.StreamObserver<LaptopServiceOuterClass.UploadImageResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class LaptopServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LaptopServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return LaptopServiceOuterClass.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LaptopService");
    }
  }

  private static final class LaptopServiceFileDescriptorSupplier
      extends LaptopServiceBaseDescriptorSupplier {
    LaptopServiceFileDescriptorSupplier() {}
  }

  private static final class LaptopServiceMethodDescriptorSupplier
      extends LaptopServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LaptopServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (LaptopServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LaptopServiceFileDescriptorSupplier())
              .addMethod(getCreateLaptopMethod())
              .addMethod(getSearchLaptopMethod())
              .addMethod(getUploadImageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
