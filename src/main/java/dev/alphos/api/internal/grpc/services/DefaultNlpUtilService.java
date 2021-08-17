package dev.alphos.api.internal.grpc.services;

import dev.alphos.api.main.grpc.client.GrpcClient;
import dev.alphos.api.main.grpc.services.NlpUtilService;
import net.alphos.service.grpc.services.nlp.FrequencyMapRequest;
import net.alphos.service.grpc.services.nlp.FrequencyMapResponse;
import net.alphos.service.grpc.services.nlp.NlpUtilServiceGrpc;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class DefaultNlpUtilService implements NlpUtilService {

  private static final Logger logger = Logger.getLogger(DefaultNlpUtilService.class.getName());

  private final NlpUtilServiceGrpc.NlpUtilServiceBlockingStub blockingStub;

  @Inject
  public DefaultNlpUtilService(GrpcClient client) {
    this.blockingStub = NlpUtilServiceGrpc.newBlockingStub(client.channel());
  }

  @Override
  public Map<String, Integer> generateFrequencyMap(List<String> tokens) {
    FrequencyMapRequest request = FrequencyMapRequest.newBuilder()
            .addAllTokens(tokens)
            .build();
    FrequencyMapResponse response;
    try {
      response = blockingStub.generate(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return null;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return null;
    }
    logger.info("FrequencyMap generated. Status: " + response.getMessage());
    return response.getFrequencyMapMap();
  }
}
