package net.alphos.api.internal;

import net.alphos.api.main.AlphosApiClient;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@Singleton
public class DefaultAlphosApiClient implements AlphosApiClient {

  private final String host = "service.alphos.dev";
  private final int port = 222;

  private AsynchronousSocketChannel client;

  @Override
  public void connect() {
    try {
      client = AsynchronousSocketChannel.open();
      InetSocketAddress socketAddress = new InetSocketAddress(host, port);
      client.connect(socketAddress, client, new CompletionHandler<Void, AsynchronousSocketChannel>() {
        @Override
        public void completed(Void result, AsynchronousSocketChannel attachment) {}

        @Override
        public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
          System.err.println("Failed to connect to server.");
          System.err.println("Possible reasons: The hostname is unknown.");
          System.err.println("- The hostname is unknown.");
          System.err.println("- The service is currently not available. (see: https://status.alphos.dev");
          exc.printStackTrace();
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void send(String message) {
    write(client, message);
  }

  private void write(final AsynchronousSocketChannel channel, final String message) {
    ByteBuffer buffer = ByteBuffer.allocate(2048);
    buffer.put(message.getBytes(StandardCharsets.UTF_8));
    buffer.flip();
    channel.write(buffer, channel, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
      @Override
      public void completed(Integer result, AsynchronousSocketChannel attachment) {}

      @Override
      public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
        exc.printStackTrace();
      }
    });
  }

  @Override
  public void receive(Consumer<String> consumer) {
    //TODO
  }
}
