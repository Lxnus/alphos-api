package net.alphos.api.main;

import com.google.inject.ImplementedBy;
import net.alphos.api.internal.DefaultAlphosApiClient;

import java.util.function.Consumer;

@ImplementedBy(DefaultAlphosApiClient.class)
public interface AlphosApiClient {

  void connect();

  void send(String message);

  void receive(Consumer<String> consumer);
}
