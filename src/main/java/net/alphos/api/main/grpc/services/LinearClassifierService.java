package net.alphos.api.main.grpc.services;

import com.google.inject.ImplementedBy;
import net.alphos.api.internal.grpc.services.DefaultLinearClassifierService;

import java.util.List;

@ImplementedBy(DefaultLinearClassifierService.class)
public interface LinearClassifierService {

  /**
   * Creates a new linear classifier. This classifier is now available and accessible with the
   * classifierId. We use this id to identify which operation we want to execute on our classifiers.
   * With this id we are also able to create multiple classifier at the same time.
   * The classifier is now saved in our remote services. So we can reuse them after restarting our software.
   * We can access the classifier also if the main-alphos-system restarts.
   * @param classifierId identification id
   * @param X (x-data)
   * @param Y (y-data (labels))
   */
  void create(long classifierId, List<Double> X, List<Double> Y);


  /**
   * Returns the error of a given classifier by its classifierId.
   * @param classifierId identification id
   * @return error of the cluster
   */
  double error(long classifierId);


  /**
   * Returns the prediction of the given classifier by its classifierId.
   * @param x value to predict target.
   * @param classifierId identification id
   * @return prediction of the classifier
   */
  double predict(double x, long classifierId);


  /**
   * If we don't want to save our classifier data anymore we can delete it with its identification id.
   * This id is used to identify the classifier in the main-alphos-system.
   * With this method call we delete the classifier from the system and all data are deleted.
   * If we want to use this classifier again we need to setup an new one. (This may take time -> Reuse the classifier)
   * @param classifierId identification id
   */
  void delete(long classifierId);
}
