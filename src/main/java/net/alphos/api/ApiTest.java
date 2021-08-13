package net.alphos.api;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.alphos.api.internal.module.AlphosApiModule;
import net.alphos.api.main.grpc.GrpcClient;

import javax.inject.Inject;

public class ApiTest {

  @Inject
  public ApiTest(GrpcClient.Factory factory) {
    GrpcClient client = factory.create("127.0.0.1", 80);
    client.hasToken(10);
  }

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new AlphosApiModule());
    ApiTest apiTest = injector.getInstance(ApiTest.class);
  }
}
