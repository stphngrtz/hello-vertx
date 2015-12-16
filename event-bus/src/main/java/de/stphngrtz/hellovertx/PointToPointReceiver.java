package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.eventbus.EventBus;

/**
 * https://github.com/vert-x3/vertx-examples/tree/master/core-examples#point-to-point
 */
public class PointToPointReceiver extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", PointToPointReceiver.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer("ping-address", message -> {
            System.out.println("received message: " + message.body());
            message.reply("pong!");
        });
        System.out.println("receiver ready!");
    }
}
