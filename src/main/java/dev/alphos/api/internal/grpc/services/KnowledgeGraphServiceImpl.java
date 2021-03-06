package dev.alphos.api.internal.grpc.services;

import dev.alphos.api.main.grpc.client.GrpcClient;
import dev.alphos.api.main.grpc.services.KnowledgeGraphService;
import net.alphos.service.grpc.services.experiment.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KnowledgeGraphServiceImpl implements KnowledgeGraphService {

  private static final Logger logger = Logger.getLogger(KnowledgeGraphServiceImpl.class.getName());

  private final KnowledgeGraphServiceGrpc.KnowledgeGraphServiceBlockingStub blockingStub;

  public KnowledgeGraphServiceImpl(GrpcClient client) {
    this.blockingStub = KnowledgeGraphServiceGrpc.newBlockingStub(client.channel());
  }

  @Override
  public void create(long graphId) {
    SetupKnowledgeGraphRequest request = SetupKnowledgeGraphRequest.newBuilder()
            .setGraphId(graphId)
            .build();
    SetupKnowledgeGraphResponse response;
    try {
      response = blockingStub.setupKnowledgeGraph(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return;
    }
    logger.info("KnowledgeGraph created. Status: " + response.getMessage());
  }

  @Override
  public void delete(long graphId) {
    DeleteKnowledgeGraphRequest request = DeleteKnowledgeGraphRequest.newBuilder()
            .setGraphId(graphId)
            .build();
    DeleteKnowledgeGraphResponse response;
    try {
      response = blockingStub.deleteKnowledgeGraph(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return;
    }
    logger.info("KnowledgeGraph deleted. Status: " + response.getMessage());
  }

  @Override
  public void adapt(List<String> sentences, long graphId) {
    AdaptKnowledgeRequest request = AdaptKnowledgeRequest.newBuilder()
            .setGraphId(graphId)
            .addAllSentences(sentences)
            .build();
    AdaptKnowledgeResponse response;
    try {
      response = blockingStub.adaptKnowledge(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return;
    }
    logger.info("Knowledge adapted. Status: " + response.getMessage());
  }

  @Override
  public String predict(String word, long graphId) {
    PredictNextWordRequest request = PredictNextWordRequest.newBuilder()
            .setGraphId(graphId)
            .setToken(word)
            .build();
    PredictNextWordResponse response;
    try {
      response = blockingStub.predictNextWord(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return null;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return null;
    }
    return response.getPrediction();
  }

  @Override
  public String predict(List<String> history, long graphId) {
    PredictNextWordHRequest request = PredictNextWordHRequest.newBuilder()
            .setGraphId(graphId)
            .addAllTokens(history)
            .build();
    PredictNextWordHResponse response;
    try {
      response = blockingStub.predictNextHWord(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return null;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return null;
    }
    return response.getPrediction();
  }

  @Override
  public List<String> history(String word, int depth, long graphId) {
    HistoryRequest request = HistoryRequest.newBuilder()
            .setGraphId(graphId)
            .setToken(word)
            .setDepth(depth)
            .build();
    HistoryResponse response;
    try {
      response = blockingStub.history(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return null;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return null;
    }
    return response.getHistoryList();
  }
}
