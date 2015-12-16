package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;

/**
 * https://github.com/vert-x3/vertx-examples/blob/master/core-examples/README.adoc#proxy
 */
public class SimpleHttpProxy extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", SimpleHttpProxy.class.getName());
    }

    @Override
    public void start() throws Exception {
        HttpClient client = vertx.createHttpClient();
        vertx.createHttpServer().requestHandler(request -> {
            System.out.println("proxying request:" + request.uri());

            HttpClientRequest client_request = client.request(request.method(), 8282, "localhost", request.uri(), client_response -> {
                System.out.println("proxying response: " + client_response.statusCode());
                request.response().setChunked(true);
                request.response().setStatusCode(client_response.statusCode());
                request.response().headers().setAll(client_response.headers());
                client_response.handler(data -> {
                    System.out.println("proxying response body: " + data.toString("UTF-8"));
                    request.response().write(data);
                });
                client_response.endHandler(v -> request.response().end());
            });
            client_request.setChunked(true);
            client_request.headers().setAll(request.headers());
            request.handler(data -> {
                System.out.println("proxying request body: " + data.toString("UTF-8"));
                client_request.write(data);
            });
            request.endHandler(v -> client_request.end());
        }).listen(8080);
    }
}
