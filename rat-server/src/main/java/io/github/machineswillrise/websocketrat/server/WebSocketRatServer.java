package io.github.machineswillrise.websocketrat.server;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class WebSocketRatServer
{
	private DSLContext createDSL(String url)
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
		var dsl = server.createDSL("jdbc:sqlite:" + System.getProperty("user.home") + "/" + "rat.db");
		dsl.select(DSL.inline("test")).fetch();
	}
}
