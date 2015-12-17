package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

/**
 * https://github.com/vert-x3/vertx-examples/tree/master/core-examples#deploy-example
 */
public class AnyVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        System.out.println(this.getClass().getName() + " is starting..");
        System.out.println("Config: " + config());
    }

    @Override
    public void stop() throws Exception {
        System.out.println(this.getClass().getName() + " is stopping..");
    }
}
