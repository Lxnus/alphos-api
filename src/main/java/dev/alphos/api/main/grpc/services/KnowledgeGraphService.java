package dev.alphos.api.main.grpc.services;

import com.google.inject.ImplementedBy;
import dev.alphos.api.internal.grpc.services.DefaultKnowledgeGraphService;

import java.util.List;

@ImplementedBy(DefaultKnowledgeGraphService.class)
public interface KnowledgeGraphService {

  void create(long graphId);

  void delete(long graphId);

  void adapt(List<String> sentences, long graphId);

  String predict(String word, long graphId);

  List<String> history(String word, int depth, long graphId);
}
