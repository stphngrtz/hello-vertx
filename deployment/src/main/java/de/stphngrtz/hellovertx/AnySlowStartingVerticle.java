package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

/**
 * https://github.com/vert-x3/vertx-examples/tree/master/core-examples#asynchronous-deployment-example
 */
public class AnySlowStartingVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        vertx.setTimer(2000, tid -> {
            System.out.println("startup completed, Verticle is now started");
            startFuture.complete();
        });
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        vertx.setTimer(2000, tid -> {
            System.out.println("shutdown completed, Verticle is now stopped");
            stopFuture.complete();
        });
    }
}
