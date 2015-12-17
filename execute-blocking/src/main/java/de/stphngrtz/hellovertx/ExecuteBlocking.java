package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

/**
 * https://github.com/vert-x3/vertx-examples/tree/master/core-examples#execute-blocking-example
 */
public class ExecuteBlocking extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", ExecuteBlocking.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(request -> {
            // Let's say we have to call a blocking API (e.g. JDBC) to execute a query for each
            // request. We can't do this directly or it will block the event loop
            // But you can do this using executeBlocking:
            vertx.<String>executeBlocking(future -> {
                // Do the blocking operation in here
                // Imagine this was a call to a blocking API to get the result
                try {
                    Thread.sleep(500);
                }
                catch (Exception ignore) {}
                future.complete("armadillos!");
            }, result -> {
                if (result.succeeded())
                    request.response().putHeader("content-type", "text/plain").end(result.result());
                else
                    result.cause().printStackTrace();
            });
        }).listen(8080);
    }
}
