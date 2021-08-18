package dev.alphos.api.main.grpc.services;

import dev.alphos.api.internal.grpc.services.NlpUtilServiceImpl;
import dev.alphos.api.main.grpc.client.GrpcClient;

import java.util.List;
import java.util.Map;

public interface NlpUtilService {

  Map<String, Integer> generateFrequencyMap(List<String> tokens);

  class Factory {
    public static NlpUtilService create(GrpcClient client) {
      return new NlpUtilServiceImpl(client);
    }
  }
}
