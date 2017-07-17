package io.vertx.starter.web;

import javax.inject.Inject;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.starter.service.HelloService;

public class HelloController {

	public static final String PATH = "/hello";

    private final Router router;
	private final HelloService service;

    @Inject
    public HelloController(Vertx vertx, Router mainRouter, HelloService service) {
		this.router = Router.router(vertx);
		this.service = service;

		mainRouter.mountSubRouter(PATH, router);
        router.get("/:name").handler(this::getGreeting);

    }

    private void getGreeting(RoutingContext ctx){
        final String name = ctx.request().getParam("name");
        ctx.response().end(service.sayHello(name));
    }

}
