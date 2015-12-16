package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;

/**
 * https://github.com/vert-x3/vertx-examples/blob/master/core-examples/README.adoc#proxy
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
            System.out.println("got request: " + request.uri());
            for (String name : request.headers().names()) {
                System.out.println(name + ": " + request.headers().get(name));
            }
            request.handler(data -> System.out.println("got data: " + data.toString("UTF-8")));
            request.endHandler(v -> {
                request.response().setChunked(true);
                for (int i = 0; i < 10; i++) {
                    request.response().write("server-data-chunk-"+i);
                }
                request.response().end();
            });
        }).listen(8282);
    }
}
