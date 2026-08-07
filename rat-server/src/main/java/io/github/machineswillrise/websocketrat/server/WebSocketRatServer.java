package io.github.machineswillrise.websocketrat.server;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.machineswillrise.websocketrat.server.controllers.AdminController;
import io.github.machineswillrise.websocketrat.server.database.MigrationRunner;
import io.github.machineswillrise.websocketrat.server.repositories.AdminRepository;
import io.github.machineswillrise.websocketrat.server.services.AdminService;
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

		// create the services, repositories and db schema
		var migrationRunner = new MigrationRunner(dslContext);
		var adminRepository = new AdminRepository(dslContext);
		var adminService = new AdminService(adminRepository);
		var adminController = new AdminController(adminService);

		migrationRunner.runAllMigrations();

		Javalin.create(config ->
		{
			config.routes.post("/admin/set-creds", adminController::setCredentials);
			config.routes.post("/admin/login", adminController::login);
			config.routes.get("/admin/logout", ctx -> ctx.req().getSession().invalidate());
		}).start(8080);
	}
}
