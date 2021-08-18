# Official Alphos-Service API
##### A modern framework for building remote artificial intelligence services.

---

Alphos is an automatic service for building advanced remote artificial intelligence software. Alphos provides different techniques to optimize your service without using your own computation power. Alphos saves your tasks so that you are able to access different models again. 

**Warning**: Alphos & Alphos-Api are still under development and maybe not stable yet.
We are currently working on advanced A.I. techniques and intregrate all existing algorithms into our api.

If you are interested in cooperating with us or want to use stable versions of our service then contact us: **_contact@alphos.dev_**
We also would love to chat with you on our [Discord Server](https://discord.gg/YfWmtNcgCY).

## Verification

To use our api, you need to request a valid certificate. This is required to get access to our api.\
You can easily request a certificate by writing us an message via:\
**E-Mail:** _contact@alphos.dev_\
**Discord:** Lxnus#2222

This certificate must be saved in `src/main/java/resources` or generally it must be placed in your resource folder.
After it, you can load a `SslContext` with this method:
```java
public SslContext loadSSLCredentials() throws SSLException {
    ClassLoader classLoader = getClass().getClassLoader();
    File serverCACertFile = new File(classLoader.getResource("ca-cert.pem").getFile());
    return GrpcSslContexts.forClient()
            .trustManager(serverCACertFile)
            .build();
}
```
Otherwise, you need to specify another file location of the certificate.

### Table of Contents
1. [Motivation](#motivation)
2. [Examples](#examples)
3. [Architecture](#architecture)
4. [Experiment](#experiment)
5. [Coming Soon](#coming-soon)

## Motivation
Today there are many frameworks that are able to present very well performing A.I. algorithms like
PyTorch, Tensorflow & Dl4j. The Problem is that all computations are performed on the 
client's computer. This may cost you hours to train a model or set up a model.

Our system is designed to build much easier advanced and complex models, that are computed remotely. We also provide algorithms
that are specially designed to compute problem with new algorithms. With it you can optimize your services.
Alphos is able to handle and manage your service by its own. You do not need to setup big database or buy computation power.
Alphos does this all for you with its new optimized algorithms.

## Examples
For the services we use Factories to generate our instances of our Service.
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
We use Interface to generate our service instances. We can generate a service with its Factory class.
```java
GrpcClient client = GrpcClient.Factory.create();
client.start(...);

KnowledgeGraphService service = KnowledgeGraphService.Factory.create(client);
service.create(...);
...
```

## Experiment
With our newest implementation wie would love you to introduce the **NGKG** _(N-Gram-Knowledge-Graph)_ Algorithm.
You are now able to create an adaptive learning knowledge-graph via the n-gram prediction and preprocess technique. 
Example: 
```java
public void knowledgeGraph() {
    long graphId = 3;
    String sentence1 = "Hello, my name is Linus!";
    String sentence2 = "Linus likes computers!";
    String sentence3 = "He is working as research scientist.";
    String sentence4 = "Linus computer saves the alphos-project";
    String sentence5 = "Linus research project is alphos";
    List<String> sentences = Arrays.asList(sentence1, sentence2, sentence3, sentence4, sentence5);
    
    KnowledgeGraphService knowledgeGraphService = KnowledgeGraphService.Factory.create(client);
    knowledgeGraphService.create(graphId);
    knowledgeGraphService.adapt(sentences, graphId);
    String predict = knowledgeGraphService.predict("Linus", graphId);
    List<String> history = knowledgeGraphService.history("alphos-project", 4, graphId);
    knowledgeGraphService.delete(graphId);
    
    System.out.println("KnowledgeGraph-Prediction: Linus -> " + predict);
    System.out.println("KnowledgeGraph-History: " + history);
}
```
This graph can adapt an learn more information by itself. The graph has an very low time-complexity and is able 
to generate very advanced knowledge. It will learn with every interaction an can perform advanced prediction task.
With every interaction it will learn more about language. In future it will be able to build knowledge with multiple
classes such Text/Actions/...

## Coming soon
Until now, the main focus was on the main-alphos-system. Currently, we are 
working on implementing all our existing services into our api.
We are strongly working on automatically and advanced natural language analysis.
Another goal is to implement more algorithms used in the field of 
artificial general intelligence to integrate advanced AGI algorithms into commercial usage.
