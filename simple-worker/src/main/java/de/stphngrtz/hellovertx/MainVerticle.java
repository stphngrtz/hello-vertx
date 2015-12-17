package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;

/**
 * https://github.com/vert-x3/vertx-examples/tree/master/core-examples#worker-verticle-example
 */
public class MainVerticle extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", MainVerticle.class.getName());
    }

    @Override
    public void start() throws Exception {
        System.out.println("Main starting in " + Thread.currentThread().getName());
        vertx.deployVerticle(WorkerVerticle.class.getName(), new DeploymentOptions().setWorker(true));

        vertx.eventBus().send("sample.data", "hello vert.x", result -> {
            System.out.println("Main received reply '" + result.result().body() + "' in " + Thread.currentThread().getName());
        });

        final int[] i = {1};
        vertx.setPeriodic(100, h -> vertx.eventBus().send("sample.data", "hello vert.x (" + i[0]++ + ")", result -> {
            System.out.println("Main received reply '" + result.result().body() + "' in " + Thread.currentThread().getName());
        }));
    }
}
