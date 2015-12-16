package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.http.HttpClientRequest;

/**
 * https://github.com/vert-x3/vertx-examples/blob/master/core-examples/README.adoc#proxy
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
        HttpClientRequest request = vertx.createHttpClient().put(8080, "localhost", "/", response -> {
            System.out.println("got response: " + response.statusCode());
            response.bodyHandler(body -> {
                System.out.println("got data: " + body.toString("UTF-8"));
            });
        });

        request.setChunked(true);
        for (int i = 0; i < 10; i++) {
            request.write("client-chunk-"+i);
        }
        request.end();
    }
}
