package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

public class PublishSubscribe extends AbstractVerticle {

    public static void main(String[] args) {
        Launcher.executeCommand("run", PublishSubscribe.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.deployVerticle(PublishSubscribeReceiver.class.getName());
        vertx.deployVerticle(PublishSubscribeSender.class.getName());
    }
}
