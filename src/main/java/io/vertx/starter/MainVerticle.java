package io.vertx.starter;

import com.google.inject.Inject;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.starter.config.AppConfig;

public class MainVerticle extends AbstractVerticle {

	private final AppConfig config;
	private final Router router;

	@Inject
	public MainVerticle(AppConfig config, Router router) {
		this.config = config;
		this.router = router;
	}

	@Override
	public void start(Future<Void> future) {

		System.out.println("Start main verticle");

		router.get("/").handler(ctx -> ctx.response().end("Hello Vert.x!"));

		// mainRouter.mountSubRouter(API.HELLO_API, helloRouter);

		// mainRouter.route().failureHandler(GlobalHandlers::error);

		// Create the http server and pass it the router
		vertx.createHttpServer().requestHandler(router::accept).listen(config.serverPort(), res -> {
			if (res.succeeded()) {
				System.out.println("Server listening on port " + config.serverPort());
				future.complete();
			} else {
				System.out.println("Failed to launch server");
				future.fail(res.cause());
			}
		});

	}

}
