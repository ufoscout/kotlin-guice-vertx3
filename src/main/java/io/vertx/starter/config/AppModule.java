/*******************************************************************************
 * Copyright 2017 Francesco Cina'
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package io.vertx.starter.config;

import java.util.concurrent.ExecutionException;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.starter.service.HelloService;
import io.vertx.starter.web.HelloController;

public class AppModule extends AbstractModule {

	@Provides
	@Singleton
	public Router mainRouter(Vertx vertx, AppConfig config) throws InterruptedException, ExecutionException {
		System.out.println("create router");
        final Router mainRouter = Router.router(vertx);
        mainRouter.route().consumes("application/json");
        mainRouter.route().produces("application/json");

        mainRouter.route().handler(BodyHandler.create());

        //mainRouter.route().failureHandler(GlobalHandlers::error);

		return mainRouter;
	}

	@Provides
	@Singleton
	public AppConfig appConfig() {
		System.out.println("create config");
		return new AppConfig(8081, 3);
	}

	@Provides
	@Singleton
	public HelloService helloService(AppConfig config) {
		System.out.println("create hello service");
		return new HelloService(config);
	}

	/*
	@Provides
	@EagerSing
	public HelloController helloController(Vertx vertx, Router mainRouter, HelloService service) {
		System.out.println("create hello controller");
		return new HelloController(vertx, mainRouter, service);
	}
	*/

	@Override
	protected void configure() {
		bind(HelloController.class).asEagerSingleton();
	}

}
