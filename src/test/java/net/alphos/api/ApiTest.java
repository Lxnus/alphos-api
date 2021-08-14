package net.alphos.api;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.alphos.api.main.grpc.services.DictionaryService;
import net.alphos.api.main.grpc.services.LinearClassifierService;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class ApiTest {

  @Inject
  public ApiTest(LinearClassifierService classifier,
                 DictionaryService dictionaryService) {
    long classifierId = 1;

    List<Double> x = Arrays.asList(1.5, 1.4, 1.3, 1.25, 1.19);
    List<Double> y = Arrays.asList(7.61, 7.10, 6.59, 6.34, 6.03);

    classifier.create(classifierId, x, y);
    double error = classifier.error(classifierId);
    double prediction = classifier.predict(1.35, classifierId);
    classifier.delete(classifierId);

    System.out.printf("Classifier: error=%s, prediction=%s \n", error, prediction);

    long dictionaryId = 2;
    dictionaryService.create(dictionaryId);
    dictionaryService.addEntry("Lxnus", 1, dictionaryId);
    String token = dictionaryService.getToken(1, dictionaryId);
    int value = dictionaryService.getValue("Lxnus", dictionaryId);
    dictionaryService.delete(dictionaryId);

    System.out.printf("Dictionary: token=%s, value=%s \n", token, value);
  }

  public static void main(String[] args) {
    Injector injector = Guice.createInjector();
    injector.getInstance(ApiTest.class);
  }
}
