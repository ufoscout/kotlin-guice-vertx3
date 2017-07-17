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
package io.vertx.starter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.intapp.vertx.guice.GuiceVerticleFactory;
import com.intapp.vertx.guice.GuiceVertxDeploymentManager;
import com.intapp.vertx.guice.VertxModule;

import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.starter.config.AppModule;

public class Main {

    /**
     * Main entry point.
     *
     * @param args the user command line arguments. For supported command line arguments please see {@link Launcher}.
     */
    public static void main(String[] args) {
        new Main().launch(h -> {}, new AppModule());
    }

    public Injector launch(Handler<AsyncResult<String>> handler, Module... modules) {
        final Vertx vertx = Vertx.vertx(new VertxOptions());

        final Module[] newArr = new Module[modules.length + 1];
        System.arraycopy(modules, 0, newArr, 1, modules.length);
        newArr[0] = new VertxModule(vertx);

        final Injector injector = Guice.createInjector(newArr);

        final GuiceVerticleFactory guiceVerticleFactory = new GuiceVerticleFactory(injector);
        vertx.registerVerticleFactory(guiceVerticleFactory);

        final GuiceVertxDeploymentManager deploymentManager = new GuiceVertxDeploymentManager(vertx);
		deploymentManager.deployVerticle(MainVerticle.class, new DeploymentOptions(), handler);

		return injector;
	}

}
