package io.github.machineswillrise.websocketrat.server.database;

import org.jooq.DSLContext;
import static org.jooq.impl.DSL.constraint;
import static org.jooq.impl.DSL.field;
import org.jooq.impl.SQLDataType;

public class MigrationRunner {
	private final DSLContext dsl;

	public MigrationRunner(DSLContext dsl)
	{
		this.dsl = dsl;
	}

	private void createAdmin()
	{
		dsl.createTableIfNotExists("admin")
			.column("id", SQLDataType.INTEGER.notNull())
			.column("username", SQLDataType.VARCHAR(50))
			.column("password_hash", SQLDataType.VARCHAR(255))
			.column("updated_at", SQLDataType.TIMESTAMP
				.nullable(false)
				.defaultValue(field("CURRENT_TIMESTAMP", SQLDataType.TIMESTAMP))
			)
			.column("first_run", SQLDataType.BOOLEAN)
			.constraints(
				constraint("single_user_check").check(field("id", SQLDataType.INTEGER).eq(1))
			)
			.execute();
	}

	public void runAllMigrations()
	{
		createAdmin();
	}
}
