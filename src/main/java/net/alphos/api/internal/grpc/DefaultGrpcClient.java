package net.alphos.api.internal.grpc;

import com.google.inject.assistedinject.Assisted;
import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import net.alphos.api.main.grpc.GrpcClient;
import net.alphos.service.grpc.*;

import javax.inject.Inject;
import javax.net.ssl.SSLException;
import java.io.File;

public class DefaultGrpcClient implements GrpcClient {

  private ManagedChannel channel;
  private DictionaryServiceGrpc.DictionaryServiceBlockingStub blockingStub;

  @Inject
  public DefaultGrpcClient(@Assisted("host") String host,
                           @Assisted("port") int port) {
    try {
      SslContext sslContext = loadSSLCredentials();
      channel = NettyChannelBuilder.forAddress(host, port)
              .sslContext(sslContext)
              .build();
      blockingStub = DictionaryServiceGrpc.newBlockingStub(channel);
    } catch (SSLException e) {
      e.printStackTrace();
    }
  }

  private SslContext loadSSLCredentials() throws SSLException {
    File serverCACertFile = new File("properties/ca-cert.pem");
    return GrpcSslContexts.forClient()
            .trustManager(serverCACertFile)
            .build();
  }

  @Override
  public void hasToken(int value) {
    ReverseDictRequest request = ReverseDictRequest.newBuilder()
            .setValue(value)
            .build();
    ReverseDictResponse response = blockingStub.hasToken(request);
    System.out.println(response);
  }

  @Override
  public void shutdown() {
    if(channel != null) {
      channel.shutdownNow();
    }
  }
}
