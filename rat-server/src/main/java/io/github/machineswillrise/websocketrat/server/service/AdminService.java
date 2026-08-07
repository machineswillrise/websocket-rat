package io.github.machineswillrise.websocketrat.server.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public class AdminService
{
	private static final int ITERATIONS = 10;
	private static final int MEMORY = 65536;
	private static final int PARALLELISM = 1;

	private final DSLContext dsl;
	private final Argon2 argon2;

	public AdminService(DSLContext dsl)
	{
		this.dsl = dsl;
		argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
	}

	private boolean canSetCredentials()
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
			Boolean isFirstRun = dsl.select(DSL.field("first_run", Boolean.class))
				.from("admin")
				.where(DSL.field("id").eq(1))
				.fetchOne(0, Boolean.class);
			return isFirstRun != null && isFirstRun;
		}
	}

	public void setCredentials(String username, String password)
	{
		if (username == null || password == null)
			throw new IllegalArgumentException("Username and password are required");
		if (username.length() > 32 || username.length() < 8 || password.length() > 32 || password.length() < 8)
			throw new IllegalArgumentException("Username and password lengths must be between 8 and 32 characters");
		if (!canSetCredentials())
			throw new IllegalArgumentException("The admin account is already set up.");

		String hash = argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password.toCharArray());

		dsl.insertInto(DSL.table("admin"))
			.columns(DSL.field("username"), DSL.field("password_hash"), DSL.field("first_run"))
			.values(username, hash, false)
			.execute();
	}
}
