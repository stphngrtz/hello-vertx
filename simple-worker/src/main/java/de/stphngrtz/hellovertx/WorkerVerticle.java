package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;

/**
 * https://github.com/vert-x3/vertx-examples/tree/master/core-examples#worker-verticle-example
 */
public class WorkerVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        System.out.println("Worker starting in " + Thread.currentThread().getName());

        vertx.eventBus().consumer("sample.data", message -> vertx.setTimer(200, h -> {
            System.out.println("Worker consuming data '" + message.body() + "' in " + Thread.currentThread().getName());
            message.reply(((String) message.body()).toUpperCase());
        }));
    }
}
