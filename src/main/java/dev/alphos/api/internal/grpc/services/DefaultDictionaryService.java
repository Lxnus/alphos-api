package dev.alphos.api.internal.grpc.services;

import dev.alphos.api.main.grpc.client.GrpcClient;
import dev.alphos.api.main.grpc.services.DictionaryService;
import net.alphos.service.grpc.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class DefaultDictionaryService implements DictionaryService {

  private final static Logger logger = Logger.getLogger(DefaultDictionaryService.class.getName());

  private final DictionaryServiceGrpc.DictionaryServiceBlockingStub blockingStub;

  @Inject
  public DefaultDictionaryService(GrpcClient client) {
    this.blockingStub = DictionaryServiceGrpc.newBlockingStub(client.channel());
  }

  @Override
  public void create(long dictionaryId) {
    SetupDictionaryRequest request = SetupDictionaryRequest.newBuilder()
            .setDictionaryId(dictionaryId)
            .build();
    SetupDictionaryResponse response;
    try {
      response = blockingStub.setupDictionary(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return;
    }
    logger.info("Dictionary created. Status: " + response.getMessage());
  }

  @Override
  public void delete(long dictionaryId) {
    DeleteDictionaryRequest request = DeleteDictionaryRequest.newBuilder()
            .setDictionaryId(dictionaryId)
            .build();
    DeleteDictionaryResponse response;
    try {
      response = blockingStub.deleteDictionary(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return;
    }
    logger.info("Dictionary deleted. Status: " + response.getMessage());
  }

  @Override
  public void addEntry(String token, int value, long dictionaryId) {
    AddDictionaryEntryRequest request = AddDictionaryEntryRequest.newBuilder()
            .setDictionaryId(dictionaryId)
            .setToken(token)
            .setValue(value)
            .build();
    AddDictionaryEntryResponse response;
    try {
      response = blockingStub.addDictionaryEntry(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return;
    }
    logger.info("Entry added. Status: " + response.getMessage());
  }

  @Override
  public Integer getValue(String token, long dictionaryId) {
    GetDictionaryValueRequest request = GetDictionaryValueRequest.newBuilder()
            .setDictionaryId(dictionaryId)
            .setToken(token)
            .build();
    GetDictionaryResponse response;
    try {
      response = blockingStub.getDictionaryValue(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return null;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return null;
    }
    return response.getValue();
  }

  @Override
  public String getToken(int value, long dictionary) {
    GetDictionaryTokenRequest request = GetDictionaryTokenRequest.newBuilder()
            .setDictionaryId(dictionary)
            .setValue(value)
            .build();
    GetDictionaryResponse response;
    try {
      response = blockingStub.getDictionaryToken(request);
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "Request failed: " + exception.getMessage());
      return null;
    }
    if(!response.getSuccess()) {
      logger.log(Level.SEVERE, response.getMessage());
      return null;
    }
    return response.getToken();
  }
}
