package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

/**
 * https://github.com/vert-x3/vertx-examples/blob/master/core-examples/README.adoc#http-server-sharing
 */
public class HttpClient extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", HttpClient.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.setPeriodic(1000, l -> {
            vertx.createHttpClient().getNow(8080, "localhost", "/", response -> {
                response.bodyHandler(body -> {
                    System.out.println(body.toString("UTF-8"));
                });
            });
        });
    }
}
