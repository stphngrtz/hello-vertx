package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;

/**
 * https://github.com/vert-x3/vertx-examples/blob/master/core-examples/README.adoc#http-server-sharing
 */
public class HttpServer extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", HttpServer.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.deployVerticle(
                HttpServerVerticle.class.getName(),
                new DeploymentOptions().setInstances(2)
        );
    }
}
