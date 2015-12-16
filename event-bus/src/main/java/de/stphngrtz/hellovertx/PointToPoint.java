package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

public class PointToPoint extends AbstractVerticle {

    public static void main(String[] args) {
        Launcher.executeCommand("run", PointToPoint.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.deployVerticle(PointToPointReceiver.class.getName());
        vertx.deployVerticle(PointToPointSender.class.getName());
    }
}
