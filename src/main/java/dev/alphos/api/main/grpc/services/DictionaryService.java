package dev.alphos.api.main.grpc.services;

import dev.alphos.api.internal.grpc.services.DictionaryServiceImpl;
import dev.alphos.api.main.grpc.client.GrpcClient;

public interface DictionaryService {

  void create(long dictionaryId);

  void delete(long dictionaryId);

  void addEntry(String token, int value, long dictionaryId);

  Integer getValue(String token, long dictionaryId);

  String getToken(int value, long dictionary);

  class Factory {
    public static DictionaryService create(GrpcClient client) {
      return new DictionaryServiceImpl(client);
    }
  }
}
