package dev.alphos.api.main.grpc.client;

import com.google.inject.ImplementedBy;
import dev.alphos.api.internal.grpc.client.DefaultGrpcClient;
import io.grpc.ManagedChannel;

@ImplementedBy(DefaultGrpcClient.class)
public interface GrpcClient {

  void start(String host, int port);

  ManagedChannel channel();

  void stop();
}
