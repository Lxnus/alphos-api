package dev.alphos.api;

import dev.alphos.api.main.grpc.client.GrpcClient;
import dev.alphos.api.main.grpc.services.DictionaryService;
import dev.alphos.api.main.grpc.services.KnowledgeGraphService;
import dev.alphos.api.main.grpc.services.LinearClassifierService;
import io.grpc.netty.GrpcSslContexts;
import io.netty.handler.ssl.SslContext;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ApiTest {

  public ApiTest() throws SSLException {
    GrpcClient client = GrpcClient.Factory.create();
    SslContext sslContext = loadSSLCredentials();
    client.start(sslContext);

    long classifierId = 1;
    List<Double> x = Arrays.asList(1.5, 1.4, 1.3, 1.25, 1.19);
    List<Double> y = Arrays.asList(7.61, 7.10, 6.59, 6.34, 6.03);

    LinearClassifierService classifier = LinearClassifierService.Factory.create(client);
    classifier.create(classifierId, x, y);
    double error = classifier.error(classifierId);
    double prediction = classifier.predict(1.35, classifierId);
    classifier.delete(classifierId);

    System.out.printf("Classifier: error=%s, prediction=%s \n", error, prediction);

    long dictionaryId = 2;
    DictionaryService dictionaryService = DictionaryService.Factory.create(client);
    dictionaryService.create(dictionaryId);
    dictionaryService.addEntry("Lxnus", 1, dictionaryId);
    String token = dictionaryService.getToken(1, dictionaryId);
    int value = dictionaryService.getValue("Lxnus", dictionaryId);
    dictionaryService.delete(dictionaryId);

    System.out.printf("Dictionary: token=%s, value=%s \n", token, value);

    long graphId = 3;
    String sentence1 = "Hello, my name is Linus!";
    String sentence2 = "Linus likes computers!";
    String sentence3 = "He is working as research scientist.";
    String sentence4 = "Linus computer saves the alphos-project";
    String sentence5 = "Linus research project is alphos";
    List<String> sentences = Arrays.asList(
            sentence1,
            sentence2,
            sentence3,
            sentence4,
            sentence5);
    KnowledgeGraphService knowledgeGraphService = KnowledgeGraphService.Factory.create(client);
    knowledgeGraphService.create(graphId);
    knowledgeGraphService.adapt(sentences, graphId);
    String predict = knowledgeGraphService.predict("Linus", graphId);
    List<String> history = knowledgeGraphService.history("alphos-project", 4, graphId);
    knowledgeGraphService.delete(graphId);
    System.out.println("KnowledgeGraph-Prediction: Linus -> " + predict);
    System.out.println("KnowledgeGraph-History: " + history);
  }

  private SslContext loadSSLCredentials() throws SSLException {
    ClassLoader classLoader = getClass().getClassLoader();
    File serverCACertFile = new File(classLoader.getResource("ca-cert.pem").getFile());
    return GrpcSslContexts.forClient()
            .trustManager(serverCACertFile)
            .build();
  }

  public static void main(String[] args) throws SSLException {
    new ApiTest();
  }
}
