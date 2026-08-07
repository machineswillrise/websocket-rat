package io.github.machineswillrise.websocketrat.server;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.machineswillrise.websocketrat.server.database.MigrationRunner;
import io.github.machineswillrise.websocketrat.server.service.AdminService;
import io.javalin.Javalin;

public class WebSocketRatServer
{
	public DSLContext createDSLContext(String url)
	{
		var config = new HikariConfig();
		config.setDriverClassName("org.sqlite.JDBC");
		config.setJdbcUrl(url);
		config.setMaximumPoolSize(5);
		config.setConnectionTestQuery("SELECT 1");

		var dataSource = new HikariDataSource(config);
		return DSL.using(dataSource, SQLDialect.SQLITE);
	}

	public static void main(String[] args)
	{
		var server = new WebSocketRatServer();
		var dslContext = server.createDSLContext("jdbc:sqlite:" + System.getProperty("user.home") + "/" + "rat.db");

		// create the services and db schema
		var migrationRunner = new MigrationRunner(dslContext);
		var adminService = new AdminService(dslContext);
		migrationRunner.runAllMigrations();

		Javalin.create(config ->
		{
			config.routes.post("/admin/set-creds", ctx ->
			{
				String username = ctx.queryParam("username");
				String password = ctx.queryParam("password");

				try
				{
					adminService.setCredentials(username, password);
				}
				catch (IllegalArgumentException e)
				{
					ctx.status(400);
				}

				ctx.status(200);
			});
		}).start(8080);
	}
}
