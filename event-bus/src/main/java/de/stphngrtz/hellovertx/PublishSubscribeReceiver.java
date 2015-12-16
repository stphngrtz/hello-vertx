package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

/**
 * https://github.com/vert-x3/vertx-examples/tree/master/core-examples#publish--subscribe
 */
public class PublishSubscribeReceiver extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", PublishSubscribeReceiver.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer("news-feed", news -> {
            System.out.println("received news: " + news.body());
        });
        System.out.println("receiver ready!");
    }
}
