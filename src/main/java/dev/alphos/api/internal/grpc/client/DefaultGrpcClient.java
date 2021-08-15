package dev.alphos.api.internal.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import dev.alphos.api.main.grpc.client.GrpcClient;

import javax.inject.Singleton;
import javax.net.ssl.SSLException;
import java.io.File;

@Singleton
public class DefaultGrpcClient implements GrpcClient {

  private ManagedChannel channel;

  public DefaultGrpcClient() {
    start("service.alphos.dev", 222);
  }

  @Override
  public void start(String host, int port) {
    try {
      SslContext sslContext = loadSSLCredentials();
      channel = NettyChannelBuilder.forAddress(host, port)
              .sslContext(sslContext)
              .build();
    } catch (SSLException e) {
      e.printStackTrace();
    }
  }

  private SslContext loadSSLCredentials() throws SSLException {
    ClassLoader classLoader = getClass().getClassLoader();
    File serverCACertFile = new File(classLoader.getResource("ca-cert.pem").getFile());
    return GrpcSslContexts.forClient()
            .trustManager(serverCACertFile)
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
