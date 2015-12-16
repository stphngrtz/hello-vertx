package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

/**
 * https://github.com/vert-x3/vertx-examples/tree/master/core-examples#publish--subscribe
 */
public class PublishSubscribeSender extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", PublishSubscribeSender.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.setPeriodic(1000, v -> {
            vertx.eventBus().publish("news-feed", "some news");
        });
    }
}
