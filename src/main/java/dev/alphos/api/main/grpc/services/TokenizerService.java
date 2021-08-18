package dev.alphos.api.main.grpc.services;

import dev.alphos.api.internal.grpc.services.TokenizerServiceImpl;
import dev.alphos.api.main.grpc.client.GrpcClient;

import java.util.List;

public interface TokenizerService {

  List<String> tokenize(String sentence);

  List<String> tokenize(List<String> sentences);

  class Factory {
    public static TokenizerService create(GrpcClient client) {
      return new TokenizerServiceImpl(client);
    }
  }
}
