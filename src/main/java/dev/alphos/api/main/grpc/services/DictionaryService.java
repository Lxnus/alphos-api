package dev.alphos.api.main.grpc.services;

import com.google.inject.ImplementedBy;
import dev.alphos.api.internal.grpc.services.DefaultDictionaryService;

@ImplementedBy(DefaultDictionaryService.class)
public interface DictionaryService {

  void create(long dictionaryId);

  void delete(long dictionaryId);

  void addEntry(String token, int value, long dictionaryId);

  Integer getValue(String token, long dictionaryId);

  String getToken(int value, long dictionary);
}
