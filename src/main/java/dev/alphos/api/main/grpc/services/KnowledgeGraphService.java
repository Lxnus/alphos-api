package dev.alphos.api.main.grpc.services;

import dev.alphos.api.internal.grpc.services.KnowledgeGraphServiceImpl;
import dev.alphos.api.main.grpc.client.GrpcClient;

import java.util.List;

public interface KnowledgeGraphService {

  void create(long graphId);

  void delete(long graphId);

  void adapt(List<String> sentences, long graphId);

  String predict(String word, long graphId);

  String predict(List<String> history, long graphId);

  List<String> history(String word, int depth, long graphId);

  class Factory {
    public static KnowledgeGraphService create(GrpcClient client) {
      return new KnowledgeGraphServiceImpl(client);
    }
  }
}
