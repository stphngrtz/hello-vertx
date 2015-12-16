package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

/**
 * https://github.com/vert-x3/vertx-examples/blob/master/core-examples/README.adoc#simple
 */
public class SimpleHttpClient extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", SimpleHttpClient.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpClient().getNow(8080, "localhost", "/", response -> {
            System.out.println("got response: " + response.statusCode());
            response.bodyHandler(body -> {
                System.out.println("got data: " + body.toString("UTF-8"));
            });
        });
    }
}
