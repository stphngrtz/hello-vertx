package de.stphngrtz.hellovertx;

import io.vertx.core.Vertx;

public class Embedding {

    /**
     * https://github.com/vert-x3/vertx-examples/blob/master/core-examples/README.adoc#embedding
     */
    public static void main(String[] args) {
        Vertx.vertx().createHttpServer().requestHandler(req -> req.response().end("Hello World!")).listen(8080);
    }
}
