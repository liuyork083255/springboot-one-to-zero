/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;

/**
 * Listener for the {@link SpringApplication} {@code run} method.
 * {@link SpringApplicationRunListener}s are loaded via the {@link SpringFactoriesLoader}
 * and should declare a public constructor that accepts a {@link SpringApplication}
 * instance and a {@code String[]} of arguments. A new
 * {@link SpringApplicationRunListener} instance will be created for each run.
 *
 * @author Phillip Webb
 * @author Dave Syer
 * @since 1.0.0
 *
 * otz:
 * 	SpringApplicationRunListener 接口的作用主要就是在 Spring Boot 启动初始化的过程中
 * 	可以通过 SpringApplicationRunListener 接口回调来让用户在启动的各个流程中可以加入自己的逻辑
 *
 * 下面 5 个方法分别代表 spring-boot 启动的 5 个阶段，也就是对应 {@link SpringApplication#run(String...)} 方法里面阶段
 *
 * 这个类如何使用？
 * 	1 自定义实现 SpringApplicationRunListener 类
 * 	2 这个自定义类必须包含 (SpringApplication application, String[]  args) 构造方法
 * 	3 在 spring.factories 中配置自定义 SpringApplicationRunListener
 * 	  e.g.
 * 	  	org.springframework.boot.SpringApplicationRunListener=liu.york.HelloApplicationRunListener
 * 	  之所以要配置在这里，是因为 spring-boot 启动的时候通过 SpringFactoriesLoader.loadFactoryNames(type, classLoader) 加载
 *
 */
public interface SpringApplicationRunListener {

	/**
	 * Called immediately when the run method has first started. Can be used for very
	 * early initialization.
	 *
	 * 在run()方法开始执行时，该方法就立即被调用，可用于在初始化最早期时做一些工作
	 */
	void starting();

	/**
	 * Called once the environment has been prepared, but before the
	 * {@link ApplicationContext} has been created.
	 * @param environment the environment
	 *
	 * 当environment构建完成，ApplicationContext创建之前，该方法被调用
	 */
	void environmentPrepared(ConfigurableEnvironment environment);

	/**
	 * Called once the {@link ApplicationContext} has been created and prepared, but
	 * before sources have been loaded.
	 * @param context the application context
	 *
	 * 当ApplicationContext构建完成时，该方法被调用
	 */
	void contextPrepared(ConfigurableApplicationContext context);

	/**
	 * Called once the application context has been loaded but before it has been
	 * refreshed.
	 * @param context the application context
	 *
	 * 在 ApplicationContext 完成加载，但没有被刷新前，该方法被调用
	 */
	void contextLoaded(ConfigurableApplicationContext context);

	/**
	 * Called immediately before the run method finishes.
	 * @param context the application context or null if a failure occurred before the
	 * context was created
	 * @param exception any run exception or null if run completed successfully.
	 *
	 * 查看源码发现 spring-boot 启动完成或者启动失败，都会调用这个方法
	 * 如果 exception 为空，则表示成功启动，否则启动异常
	 */
	void finished(ConfigurableApplicationContext context, Throwable exception);

}
