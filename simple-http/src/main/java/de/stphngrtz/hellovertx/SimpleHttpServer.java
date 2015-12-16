package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;

/**
 * https://github.com/vert-x3/vertx-examples/blob/master/core-examples/README.adoc#simple
 */
public class SimpleHttpServer extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", SimpleHttpServer.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(request -> {
            request.response().putHeader("content-type", "text/html").end("<html><body><h1>hello from vert.x</h1></body></html>");
        }).listen(8080);
    }
}
