package io.github.machineswillrise.websocketrat.server;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.javalin.Javalin;
import io.javalin.http.TooManyRequestsResponse;
import io.javalin.plugin.bundled.RateLimitPlugin;
import static io.javalin.apibuilder.ApiBuilder.*;

import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import io.github.machineswillrise.websocketrat.common.Config;
import io.github.machineswillrise.websocketrat.common.ConfigParser;

public class WebSocketRatServer
{
	private DSLContext setUpDSL(String url)
	{
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("org.sqlite.JDBC");
		config.setJdbcUrl(url);
		config.setMaximumPoolSize(5);
		config.setConnectionTestQuery("SELECT 1");

		var dataSource = new HikariDataSource(config);
		return DSL.using(dataSource, SQLDialect.SQLITE);
	}

	private Config loadConfig()
	{
		ConfigParser parser = new ConfigParser(getClass().getResourceAsStream("/config.properties"));
		return parser.parse();
	}

	public static void main(String[] args)
	{
		// Locate database
		String databaseLocation = "jdbc:sqlite:" + System.getProperty("user.home") + "/" + "rat.db";

		// Connect to the database
		var server = new WebSocketRatServer();
		var dsl = server.setUpDSL(databaseLocation);

		// Run database migrations
		Flyway flyway = Flyway.configure()
			.dataSource(databaseLocation, null, null)
			.locations("classpath:migrations")
			.load();

		flyway.migrate();

		// Load controllers
		DIContainer container = new DIContainer(dsl);
		var adminController = container.adminController;

		// Load config
		Config config = server.loadConfig();

		Javalin.create(javalinConfig ->
		{
			javalinConfig.registerPlugin(new RateLimitPlugin());

			javalinConfig.routes.before(ctx ->
				ctx.with(RateLimitPlugin.class).requestPerTimeUnit(60, TimeUnit.MINUTES));

			javalinConfig.routes.exception(TooManyRequestsResponse.class, (e, ctx) ->
			{
				ctx.status(429);
				ctx.json(Map.of("error", "Too Many Requests", "code", "TOO_MANY_REQUESTS"));
			});

			javalinConfig.routes.exception(Exception.class, (e, ctx) ->
			{
				ctx.status(500);
				ctx.json(Map.of("error", "Internal Server Error", "code", "INTERNAL_SERVER_ERROR"));
			});

			javalinConfig.routes.apiBuilder(() ->
			{
				path("/admin", () ->
				{
					path("/set-creds", () -> post(adminController::setCredentials));
					path("/login", () -> get(adminController::login));
					path("/logout", () -> get(ctx -> ctx.req().getSession().invalidate()));
				});
			});
		}).start(config.port());
	}
}
