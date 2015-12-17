package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import io.vertx.core.json.JsonObject;

/**
 * https://github.com/vert-x3/vertx-examples/tree/master/core-examples#deploy-example
 * https://github.com/vert-x3/vertx-examples/tree/master/core-examples#asynchronous-deployment-example
 */
public class DeployingVerticle extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", DeployingVerticle.class.getName());
    }

    @Override
    public void start() throws Exception {
        System.out.println(this.getClass().getName() + " is starting..");

        // Deploy a verticle and don't wait for it to start
        vertx.deployVerticle(AnyVerticle.class.getName());

        // Deploy another instance and wait for it to start
        vertx.deployVerticle(AnySlowStartingVerticle.class.getName(), deployment -> {
            if (deployment.succeeded()) {
                System.out.println(deployment.result() + " deployed successfully");
                vertx.undeploy(deployment.result(), undeployment -> {
                    if (undeployment.succeeded())
                        System.out.println("undeployment was successful");
                    else
                        undeployment.cause().printStackTrace();
                });
            }
            else
                deployment.cause().printStackTrace();
        });

        // Deploy specifying some config
        JsonObject config = new JsonObject().put("foo", "bar");
        vertx.deployVerticle(AnyVerticle.class.getName(), new DeploymentOptions().setConfig(config));

        // Deploy 10 instances
        vertx.deployVerticle(AnyVerticle.class.getName(), new DeploymentOptions().setInstances(10));

        // Deploy it as a worker verticle
        vertx.deployVerticle(AnyVerticle.class.getName(), new DeploymentOptions().setWorker(true));
    }
}
