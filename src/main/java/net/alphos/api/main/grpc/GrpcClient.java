package net.alphos.api.main.grpc;

import com.google.inject.ImplementedBy;
import com.google.inject.assistedinject.Assisted;
import net.alphos.api.internal.grpc.DefaultGrpcClient;

@ImplementedBy(DefaultGrpcClient.class)
public interface GrpcClient {

  void hasToken(int value);

  void shutdown();

  interface Factory {
    GrpcClient create(@Assisted("host") String host,
                       @Assisted("port") int port);
  }
}
