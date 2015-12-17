package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

import java.util.HashMap;
import java.util.Map;

/**
 * https://github.com/vert-x3/vertx-examples/blob/master/web-examples/README.adoc#simple-rest-micro-service
 */
public class SimpleRestServer extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", SimpleRestServer.class.getName());
    }

    @Override
    public void start() throws Exception {
        setUp();

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route().handler(CookieHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

        router.get("/products").handler(this::listProducts);
        router.get("/products/:productId").handler(this::getProduct);
        router.put("/products/:productId").handler(this::setProduct);

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

    private Map<String, JsonObject> products = new HashMap<>();

    private void setUp() {
        products.put("prod-1", new JsonObject().put("id", "prod-1").put("name", "Egg Whisk").put("price", 3.99));
        products.put("prod-4", new JsonObject().put("id", "prod-4").put("name", "Tea Cosy").put("price", 5.99));
        products.put("prod-8", new JsonObject().put("id", "prod-8").put("name", "Spatula").put("price", 1.00));
    }

    private void listProducts(RoutingContext routingContext) {
        Integer hitcount = routingContext.session().get("hitcount");
        hitcount = ((hitcount == null) ? 0 : hitcount) + 1;
        routingContext.session().put("hitcount", hitcount);

        JsonArray jsonArray = new JsonArray().add(hitcount);
        products.forEach((k,v) -> jsonArray.add(v));
        routingContext.response().putHeader("content-type", "application/json").end(jsonArray.encodePrettily());
    }

    private void getProduct(RoutingContext routingContext) {
        String productId = routingContext.request().getParam("productId");
        if (productId == null)
            routingContext.response().setStatusCode(400).end();
        else {
            JsonObject product = products.get(productId);
            if (product == null)
                routingContext.response().setStatusCode(404).end();
            else
                routingContext.response().putHeader("content-type", "application/json").end(product.encodePrettily());
        }
    }

    private void setProduct(RoutingContext routingContext) {
        String productId = routingContext.request().getParam("productId");
        if (productId == null)
            routingContext.response().setStatusCode(400).end();
        else {
            JsonObject product = routingContext.getBodyAsJson();
            if (product == null)
                routingContext.response().setStatusCode(400).end();
            else {
                products.put(productId, product);
                routingContext.response().end();
            }

        }
    }
}
