package net.alphos.api.main.grpc.services;

public interface DictionaryService {

  int getValue(String token);

  String getToken(int value);
}
