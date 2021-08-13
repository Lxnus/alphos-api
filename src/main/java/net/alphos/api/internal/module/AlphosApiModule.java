package net.alphos.api.internal.module;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import net.alphos.api.internal.grpc.DefaultGrpcClient;
import net.alphos.api.main.grpc.GrpcClient;

public class AlphosApiModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new FactoryModuleBuilder()
            .implement(GrpcClient.class, DefaultGrpcClient.class)
            .build(GrpcClient.Factory.class));
  }
}
