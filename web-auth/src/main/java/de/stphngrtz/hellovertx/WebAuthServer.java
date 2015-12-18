package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.shiro.ShiroAuth;
import io.vertx.ext.auth.shiro.ShiroAuthOptions;
import io.vertx.ext.auth.shiro.ShiroAuthRealmType;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;

/**
 * https://github.com/vert-x3/vertx-examples/blob/master/web-examples/README.adoc#auth-example
 */
public class WebAuthServer extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", WebAuthServer.class.getName());
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(CookieHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
        router.route().handler(BodyHandler.create());

        AuthProvider authProvider = ShiroAuth.create(vertx, new ShiroAuthOptions().setType(ShiroAuthRealmType.PROPERTIES).setConfig(new JsonObject()));

        // We need a user session handler too to make sure the user is stored in the session between requests
        router.route().handler(UserSessionHandler.create(authProvider));
        // Any requests to URI starting '/private/' require login
        router.route("/private/*").handler(RedirectAuthHandler.create(authProvider, "/login.html"));
        // Serve the static private pages from directory 'private'
        router.route("/private/*").handler(StaticHandler.create().setCachingEnabled(false).setWebRoot("private"));
        // Handles the actual login
        router.route("/login").handler(FormLoginHandler.create(authProvider));

        router.route("/logout").handler(context -> {
            context.clearUser();
            context.response().putHeader("location", "/").setStatusCode(302).end();
        });

        router.route().handler(StaticHandler.create());

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}
