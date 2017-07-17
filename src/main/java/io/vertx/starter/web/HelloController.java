package io.vertx.starter.web;

import com.google.inject.Inject;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.starter.service.HelloService;

public class HelloController {

    private final Router router;
	private final HelloService service;

    @Inject
    public HelloController(Vertx vertx, Router mainRouter, HelloService service) {
		this.router = Router.router(vertx);
		this.service = service;

		mainRouter.mountSubRouter("/hello", router);
        router.get("/").handler(this::getGreeting);

    }

    private void getGreeting(RoutingContext ctx){
        final String name = ctx.request().getParam("name");
        ctx.response().end(Json.encode(service.sayHello(name)));
    }

}
