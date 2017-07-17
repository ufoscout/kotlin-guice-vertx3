package io.vertx.starter;

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
import io.vertx.starter.config.AppConfig;
import io.vertx.starter.config.AppModule;

@RunWith(VertxUnitRunner.class)
public class MainVerticleTest {

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
		vertx.createHttpClient().getNow(appConfig.serverPort(), "localhost", "/", response -> {
			tc.assertEquals(response.statusCode(), 200);
			response.bodyHandler(body -> {
				tc.assertTrue(body.length() > 0);
				async.complete();
			});
		});
	}

}