package dev.alphos.api.main.grpc.client;

import dev.alphos.api.internal.grpc.client.GrpcClientImpl;
import io.grpc.ManagedChannel;
import io.netty.handler.ssl.SslContext;

public interface GrpcClient {

  void start(SslContext sslContext);

  ManagedChannel channel();

  void stop();

  class Factory {
    public static GrpcClient create() {
      return new GrpcClientImpl();
    }
  }
}
