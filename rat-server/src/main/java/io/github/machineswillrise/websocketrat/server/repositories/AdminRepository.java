package io.github.machineswillrise.websocketrat.server.repositories;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import io.github.machineswillrise.websocketrat.server.dto.AdminCredentials;

public class AdminRepository
{
	private final DSLContext dsl;

	public AdminRepository(DSLContext dsl)
	{
		this.dsl = dsl;
	}

	public boolean isEmpty()
	{
		return dsl.fetchCount(dsl.selectFrom("admin")) == 0;
	}

	public boolean isFirstRun()
	{
		Boolean isFirstRun = dsl.select(DSL.field("first_run", Boolean.class))
			.from("admin")
			.where(DSL.field("id").eq(1))
			.fetchOne(0, Boolean.class);
		return isFirstRun != null && isFirstRun;
	}

	public void createFirstRunPlaceholder()
	{
		dsl.insertInto(DSL.table("admin"))
			.columns(DSL.field("id"), DSL.field("username"), DSL.field("password_hash"), DSL.field("first_run"))
			.values(1, "", "", true)
			.execute();
	}

	public void updateCredentials(String username, String passwordHash)
	{
		dsl.update(DSL.table("admin"))
			.set(DSL.field("username"), username)
			.set(DSL.field("password_hash"), passwordHash)
			.set(DSL.field("first_run"), false)
			.where(DSL.field("id").eq(1))
			.execute();
	}

	public AdminCredentials findCredentials()
	{
		return dsl.select(DSL.field("username", String.class), DSL.field("password_hash", String.class))
			.from("admin")
			.where(DSL.field("id").eq(1))
			.fetchOne(record -> new AdminCredentials(
				record.get(0, String.class),
				record.get(1, String.class)
			));
	}
}
