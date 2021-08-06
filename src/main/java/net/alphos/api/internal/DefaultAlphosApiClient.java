package net.alphos.api.internal;

import net.alphos.api.main.AlphosApiClient;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

@Singleton
public class DefaultAlphosApiClient implements AlphosApiClient {

  private final String host = "127.0.0.1";//"service.alphos.dev";
  private final int port = 80;//222;

  private AsynchronousSocketChannel client;

  @Override
  public void connect() {
    try {
      client = AsynchronousSocketChannel.open();
      InetSocketAddress socketAddress = new InetSocketAddress(host, port);
      Future<Void> future = client.connect(socketAddress);
      future.get();
    } catch (IOException | ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void send(String message) {
    byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
    ByteBuffer buffer = ByteBuffer.wrap(bytes);
    Future<Integer> result = client.write(buffer);
    try {
      result.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void receive(Consumer<String> consumer) {
    //TODO
  }
}
