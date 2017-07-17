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
package io.vertx.starter.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.starter.Main;
import io.vertx.starter.TestModule;
import io.vertx.starter.config.AppConfig;
import io.vertx.starter.config.AppModule;

@RunWith(VertxUnitRunner.class)
public class HelloControllerTest {

	@Inject
	private Vertx vertx;
	@Inject
	private AppConfig appConfig;

	@Before
	public void setUp(TestContext tc) {
		final Injector injector = new Main().launch(tc.asyncAssertSuccess(), Modules.override(new AppModule()).with(new TestModule()));
		injector.injectMembers(this);
	}

	@After
	public void tearDown(TestContext tc) {
		vertx.close(tc.asyncAssertSuccess());
	}

	@Test
	public void testThatTheServerIsStarted(TestContext tc) {
		final Async async = tc.async();
		vertx.createHttpClient().getNow(appConfig.serverPort(), "localhost", HelloController.PATH + "/Francesco", response -> {
			tc.assertEquals(response.statusCode(), 200);
			response.bodyHandler(body -> {
				System.out.println("body is: " + body.toString());
				tc.assertEquals("Hello, Hello, Hello, Francesco!", body.toString());
				async.complete();
			});
		});
	}

}
