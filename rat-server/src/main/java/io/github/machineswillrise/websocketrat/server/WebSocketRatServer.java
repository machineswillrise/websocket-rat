package io.github.machineswillrise.websocketrat.server;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;

import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class WebSocketRatServer
{
	private DSLContext dsl;

	public WebSocketRatServer(String url)
	{
		var config = new HikariConfig();
		config.setDriverClassName("org.sqlite.JDBC");
		config.setJdbcUrl(url);
		config.setMaximumPoolSize(5);
		config.setConnectionTestQuery("SELECT 1");

		var dataSource = new HikariDataSource(config);
		dsl = DSL.using(dataSource, SQLDialect.SQLITE);
	}

	private void createTables() {
		dsl.createTableIfNotExists("admin")
			.column("id", SQLDataType.INTEGER.notNull())
			.column("username", SQLDataType.VARCHAR(50))
			.column("password_hash", SQLDataType.VARCHAR(255))
			.column("updated_at", SQLDataType.TIMESTAMP
				.nullable(false)
				.defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.TIMESTAMP))
			)
			.constraints(
				DSL.constraint("single_user_check").check(DSL.field("id", SQLDataType.INTEGER).eq(1))
			)
			.execute();
	}

	public static void main(String[] args)
	{
		var server = new WebSocketRatServer("jdbc:sqlite:" + System.getProperty("user.home") + "/" + "rat.db");
		server.createTables();
	}
}
