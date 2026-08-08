package io.github.machineswillrise.websocketrat.server;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.machineswillrise.websocketrat.server.database.MigrationRunner;
import io.javalin.Javalin;

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

	public static void main(String[] args)
	{
		// Connect to the database
		var server = new WebSocketRatServer();
		var dsl = server.setUpDSL("jdbc:sqlite:" + System.getProperty("user.home") + "/" + "rat.db");

		// Run database migrations
		var migrationRunner = new MigrationRunner(dsl);
		migrationRunner.runAllMigrations();

		// Load controllers
		DIContainer container = new DIContainer(dsl);
		var adminController = container.adminController;

		Javalin.create(config -> {
			config.routes.post("/admin/set-creds", adminController::setCredentials);
			config.routes.get("/admin/login", adminController::login);
			config.routes.get("/admin/logout", ctx -> ctx.req().getSession().invalidate());
		}).start(8080);
	}
}
