package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.streams.Pump;

/**
 * https://github.com/vert-x3/vertx-examples/blob/master/core-examples/README.adoc#echo
 * telnet localhost 1234
 * How to quit telnet? CTRL + ALT + 6 -> quit
 */
public class EchoServer extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", EchoServer.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.createNetServer().connectHandler(netSocket -> {
            Pump.pump(netSocket, netSocket).start();
        }).listen(1234);
        System.out.println("Echo server is now listening..");
    }
}
