# Official Alphos-Service API

#####A modern framework for building remote artificial intelligence services.

---

Alphos is an automatic service for building advanced remote artificial intelligence software. Alphos provides different techniques to optimize your service without using your own computation power. Alphos saves your tasks so that you are able to access different models again. 

**Warning**: Alphos & Alphos-Api are still under development and maybe not stable yet.
We are currently working on advanced A.I. techniques and intregrate all existing algorithms into our api.

If you are interested in cooperating with us or want to use stable versions of our service then contact us: **_contact@alphos.dev_**
We also would love to chat with you on our [Discord Server](https://discord.gg/YfWmtNcgCY).

### Table of Contents
1. [Motivation](#motivation)
2. [Examples](#examples)
3. [Architecture](#architecture)
4. [Coming Soon](#coming-soon)

## Motivation
Today there are many frameworks that are able to present very well performing A.I. algorithms like
PyTorch, Tensorflow & Dl4j. The Problem is that all computations are performed on the 
client's computer. This may cost you hours to train a model or setup a model.

Our system is designed to build much easier advanced and complex models, that are computed remotely. We also provide algorithms
that are specially designed to compute problem with new algorithms. With it you can optimize your services.
Alphos is able to handle and manage your service by its own. You do not need to setup big database or buy computation power.
Alphos does this all for you with its new optimized algorithms.

## Examples
For the services we use the `@Inject` annotation for injecting our service.
To get a deeper understanding of this [architecture](#architecture) have a look at [chapter 3](#architecture).
```java
public class ApiTest {

  @Inject
  public ApiTest(LinearClassifierService classifier) {
    long classifierId = 1;

    List<Double> x = Arrays.asList(1.5, 1.4, 1.3, 1.25, 1.19);
    List<Double> y = Arrays.asList(7.61, 7.10, 6.59, 6.34, 6.03);

    /**
     * Creates a new linear classifier. This classifier is now available and accessible with the
     * classifierId. We use this id to identify which operation we want to execute on our classifiers.
     * With this id we are also able to create multiple classifier at the same time.
     * The classifier is now saved in our remote services. So we can reuse them after restarting our software.
     * We can access the classifier also if the main-alphos-system restarts.
     */
    classifier.create(classifierId, x, y);

    /**
     * Returns the error of a given classifier by its classifierId.
     */
    double error = classifier.error(classifierId);

    /**
     * Returns the prediction of the given classifier by its classifierId.
     */
    double prediction = classifier.predict(1.35, classifierId);

    /**
     * If we don't want to save our classifier data anymore we can delete it with its identification id.
     * This id is used to identify the classifier in the main-alphos-system.
     * With this method call we delete the classifier from the system and all data are deleted.
     * If we want to use this classifier again we need to setup an new one. 
     * (This may take time -> Reuse the classifier)
     */
    classifier.delete(classifierId);

    System.out.printf("Classifier: error=%s, prediction=%s \n", error, prediction);
  }

  public static void main(String[] args) {
    Injector injector = Guice.createInjector();
    injector.getInstance(ApiTest.class);
  }
}
```

## Architecture
For advanced security we use mutual SSL/TLS encryption to guarantee high security 
standards for our clients.

To access tools of the API you won't call static method but instead use constructor injection to retrieve
the instance you need. An instance of the class will be constructed automatically using Guice.
For further explanation, see the [Guice documentation](https://github.com/google/guice/wiki).

## Coming soon
Until now, the main focus was on the main-alphos-system. Know we are 
able to implement all our existing services into our api.
We are strongly working on automatically and advanced natural language analysis.
Another goal is to implement more algorithms used in the field of 
artificial general intelligence to integrate advanced AGI algorithms into commercial usage.
