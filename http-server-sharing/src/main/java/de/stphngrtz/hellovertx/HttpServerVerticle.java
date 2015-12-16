package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;

/**
 * https://github.com/vert-x3/vertx-examples/blob/master/core-examples/README.adoc#http-server-sharing
 */
public class HttpServerVerticle extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", HttpServerVerticle.class.getName(), "-instances", "4");
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(request -> {
            request.response()
                    .putHeader("content-type", "text/html")
                    .end("<html><body><h1>hello from " + this + "</h1></body></html>");
        }).listen(8080);
    }
}
