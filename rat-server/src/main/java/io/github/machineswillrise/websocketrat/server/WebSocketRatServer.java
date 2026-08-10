package io.github.machineswillrise.websocketrat.server;

import org.flywaydb.core.Flyway;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

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
		var configFile = getClass().getResourceAsStream("config.properties");
		ConfigParser parser = new ConfigParser(configFile);
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

		Javalin.create(javalinConfig -> {
			javalinConfig.routes.apiBuilder(() -> {
				path("/admin", () -> {
					path("/set-creds", () -> post(adminController::setCredentials));
					path("/login", () -> get(adminController::login));
					path("/logout", () -> get(ctx -> ctx.req().getSession().invalidate()));
				});
			});
		}).start(config.port());
	}
}
