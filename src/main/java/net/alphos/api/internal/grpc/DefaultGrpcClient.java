package net.alphos.api.internal.grpc;

import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import net.alphos.api.main.grpc.GrpcClient;

import javax.inject.Singleton;
import javax.net.ssl.SSLException;
import java.io.File;

@Singleton
public class DefaultGrpcClient implements GrpcClient {

  private ManagedChannel channel;

  public DefaultGrpcClient() {}

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
    File serverCACertFile = new File("properties/ca-cert.pem");
    File clientCertFile = new File("properties/client-cert.pem");
    File clientKeyFile = new File("properties/client-key.pem");
    return GrpcSslContexts.forClient()
            .keyManager(clientCertFile, clientKeyFile)
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
