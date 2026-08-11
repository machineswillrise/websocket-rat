package io.github.machineswillrise.websocketrat.server.repositories;

import java.util.List;

import org.jooq.DSLContext;

import static io.github.machineswillrise.websocketrat.server.db.Tables.ADMIN;
import io.github.machineswillrise.websocketrat.server.models.Admin;

public class AdminRepository implements Repository<Admin>
{
	private final DSLContext dsl;

	public AdminRepository(DSLContext dsl)
	{
		this.dsl = dsl;
	}

	@Override
	public int count()
	{
		return dsl.fetchCount(ADMIN);
	}

	@Override
	public List<Admin> readAll()
	{
		return dsl.fetch(ADMIN).map(record -> new Admin(
			record.getId(),
			record.getUsername(),
			record.getPasswordHash(),
			record.getUpdatedAt(),
			record.getAlreadyRun()
		));
	}

	public Admin readFirst()
	{
		return readAll().stream().findFirst().orElse(null);
	}

	@Override
	public void update(Admin oldRow, Admin newRow)
	{
		dsl.update(ADMIN)
			.set(ADMIN.USERNAME, newRow.username())
			.set(ADMIN.PASSWORD_HASH, newRow.passwordHash())
			.set(ADMIN.ALREADY_RUN, newRow.alreadyRun())
			.where(ADMIN.ID.eq(oldRow.id()))
			.execute();
	}

	public void createFirstRunPlaceholder()
	{
		dsl.insertInto(ADMIN)
			.set(ADMIN.ID, 1)
			.set(ADMIN.USERNAME, "")
			.set(ADMIN.PASSWORD_HASH, "")
			.set(ADMIN.ALREADY_RUN, true)
			.execute();
	}
}
