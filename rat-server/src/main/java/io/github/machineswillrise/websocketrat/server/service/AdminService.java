package io.github.machineswillrise.websocketrat.server.service;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public class AdminService
{
	private final DSLContext dsl;

	public AdminService(DSLContext dsl)
	{
		this.dsl = dsl;
	}

	public boolean canSetPassword()
	{
		if (dsl.fetchCount(dsl.selectFrom("admin")) == 0)
		{
			dsl.insertInto(DSL.table("admin"))
				.columns(DSL.field("id"), DSL.field("username"), DSL.field("password_hash"), DSL.field("first_run"))
				.values(1, "", "", true)
				.execute();

			return true;
		}

		else
		{
			return dsl.select(DSL.field("first_run", Boolean.class))
				.from("admin")
				.where(DSL.field("id").eq(1))
				.fetchOne(0, Boolean.class);
		}
	}
}
