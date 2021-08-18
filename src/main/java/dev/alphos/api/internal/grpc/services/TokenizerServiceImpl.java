package dev.alphos.api.internal.grpc.services;

import dev.alphos.api.main.grpc.client.GrpcClient;
import dev.alphos.api.main.grpc.services.TokenizerService;
import net.alphos.service.grpc.services.nlp.TokenizeRequest;
import net.alphos.service.grpc.services.nlp.TokenizeResponse;
import net.alphos.service.grpc.services.nlp.TokenizerServiceGrpc;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenizerServiceImpl implements TokenizerService {

  private static final Logger logger = Logger.getLogger(TokenizerServiceImpl.class.getName());

  private final TokenizerServiceGrpc.TokenizerServiceBlockingStub blockingStub;

  public TokenizerServiceImpl(GrpcClient client) {
    this.blockingStub = TokenizerServiceGrpc.newBlockingStub(client.channel());
  }

  @Override
  public List<String> tokenize(String sentence) {
    TokenizeRequest request = TokenizeRequest.newBuilder()
            .addSentences(sentence)
            .build();
    TokenizeResponse response;
    try {
      response = blockingStub.tokenize(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return null;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return null;
    }
    logger.info("Tokenization done. Status: " + response.getMessage());
    return response.getTokensList();
  }

  @Override
  public List<String> tokenize(List<String> sentences) {
    TokenizeRequest request = TokenizeRequest.newBuilder()
            .addAllSentences(sentences)
            .build();
    TokenizeResponse response;
    try {
      response = blockingStub.tokenize(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return null;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return null;
    }
    logger.info("Tokenization done. Status: " + response.getMessage());
    return response.getTokensList();
  }
}
