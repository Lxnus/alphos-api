package dev.alphos.api.main.grpc.services;

import com.google.inject.ImplementedBy;
import dev.alphos.api.internal.grpc.services.DefaultTokenizerService;

import java.util.List;

@ImplementedBy(DefaultTokenizerService.class)
public interface TokenizerService {

  List<String> tokenize(String sentence);

  List<String> tokenize(List<String> sentences);
}
