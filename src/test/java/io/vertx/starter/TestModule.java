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

import java.io.IOException;
import java.net.ServerSocket;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import io.vertx.starter.config.AppConfig;

public class TestModule extends AbstractModule {

	@Provides
	@Singleton
	public AppConfig appConfig() {
		System.out.println("create TestConfig");
		return new AppConfig(getFreePort(), 3);
	}

	@Override
	protected void configure() {
	}

	private synchronized int getFreePort() {
		try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (final IOException e) {
			throw new RuntimeException(e);
		}

	}
}
