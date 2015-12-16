package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.eventbus.EventBus;

/**
 * https://github.com/vert-x3/vertx-examples/tree/master/core-examples#point-to-point
 */
public class PointToPointSender extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", PointToPointSender.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.setPeriodic(1000, v -> {
            vertx.eventBus().send("ping-address", "ping!", reply -> {
                if (reply.succeeded())
                    System.out.println("received reply: " + reply.result().body());
                else
                    System.out.println("no reply");
            });
        });
    }
}
