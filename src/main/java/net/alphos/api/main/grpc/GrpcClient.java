package net.alphos.api.main.grpc;

import com.google.inject.ImplementedBy;
import io.grpc.ManagedChannel;
import net.alphos.api.internal.grpc.DefaultGrpcClient;

@ImplementedBy(DefaultGrpcClient.class)
public interface GrpcClient {

  void start(String host, int port);

  ManagedChannel channel();

  void stop();
}
