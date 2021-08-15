package dev.alphos.api.internal.grpc.services;

import dev.alphos.api.main.grpc.client.GrpcClient;
import dev.alphos.api.main.grpc.services.LinearClassifierService;
import net.alphos.service.grpc.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class DefaultLinearClassifierService implements LinearClassifierService {

  private static final Logger logger = Logger.getLogger(DefaultLinearClassifierService.class.getName());

  private final LinearClassifierServiceGrpc.LinearClassifierServiceBlockingStub blockingStub;

  @Inject
  public DefaultLinearClassifierService(GrpcClient client) {
    this.blockingStub = LinearClassifierServiceGrpc.newBlockingStub(client.channel());
  }

  @Override
  public void create(long classifierId, List<Double> X, List<Double> Y) {
    SetupLCRequest request = SetupLCRequest.newBuilder()
            .setClassifierId(classifierId)
            .addAllX(X)
            .addAllY(Y)
            .build();
    SetupLCResponse response;
    try {
      response = blockingStub.setupLinearClassifier(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return;
    }
    logger.info("Linear classifier created. Status: " + response.getMessage());
  }

  @Override
  public double error(long classifierId) {
    ErrorLCRequest request = ErrorLCRequest.newBuilder()
            .setClassifierId(classifierId)
            .build();

    ErrorLCResponse response;
    try {
      response = blockingStub.errorLinearClassifier(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return Double.NaN;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return Double.NaN;
    }
    return response.getError();
  }

  @Override
  public double predict(double x, long classifierId) {
    PredictLCRequest request = PredictLCRequest.newBuilder()
            .setClassifierId(classifierId)
            .setValue(x)
            .build();

    PredictLCResponse response;
    try {
      response = blockingStub.predictLinearClassifier(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return Double.NaN;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return Double.NaN;
    }
    return response.getPrediction();
  }

  @Override
  public void delete(long classifierId) {
    DeleteLCRequest request = DeleteLCRequest.newBuilder()
            .setClassifierId(classifierId)
            .build();

    DeleteLCResponse response;
    try {
      response = blockingStub.deleteLinearClassifier(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return;
    }
    logger.info("Linear classifier deleted. Status: " + response.getMessage());
  }
}
