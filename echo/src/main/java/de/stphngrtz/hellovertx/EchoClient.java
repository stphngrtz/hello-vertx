package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.net.NetSocket;

/**
 * https://github.com/vert-x3/vertx-examples/blob/master/core-examples/README.adoc#echo
 * telnet localhost 1234
 * How to quit telnet? CTRL + ALT + 6 -> quit
 */
public class EchoClient extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", EchoClient.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.createNetClient().connect(1234, "localhost", event -> {
            if (event.succeeded()) {
                NetSocket netSocket = event.result();
                netSocket.handler(buffer -> System.out.println("Net client receiving: " + buffer.toString("UTF-8")));

                for (int i = 0; i < 10; i++) {
                    String message = "hello " + i + "\n";
                    System.out.println("Net client sending: " + message);
                    netSocket.write(message);
                }
            }
            else {
                System.out.println("failed to connect: " + event.cause());
            }
        });
    }
}
