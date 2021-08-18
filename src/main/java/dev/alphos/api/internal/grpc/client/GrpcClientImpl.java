package dev.alphos.api.internal.grpc.client;

import dev.alphos.api.main.grpc.client.GrpcClient;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;

public class GrpcClientImpl implements GrpcClient {

  private ManagedChannel channel;

  public GrpcClientImpl() {}

  @Override
  public void start(SslContext sslContext) {
    if(sslContext == null) {
      throw new NullPointerException();
    }
    channel = NettyChannelBuilder.forAddress("service.alphos.dev", 222)
            .sslContext(sslContext)
            .build();
  }

  @Override
  public ManagedChannel channel() {
    return channel;
  }

  @Override
  public void stop() {
    if(channel != null) {
      channel.shutdownNow();
    }
  }
}
