package io.github.machineswillrise.websocketrat.server.controllers;

import io.javalin.http.Context;

import io.github.machineswillrise.websocketrat.server.dtos.LoginResponse;
import io.github.machineswillrise.websocketrat.server.models.Admin;
import io.github.machineswillrise.websocketrat.server.repositories.AdminRepository;
import io.github.machineswillrise.websocketrat.server.services.Argon2Service;

public class AdminController
{
	private final AdminRepository adminRepository;
	private final Argon2Service argon2Service;

	public AdminController(AdminRepository adminRepository, Argon2Service argon2Service)
	{
		this.adminRepository = adminRepository;
		this.argon2Service = argon2Service;
	}

	// formParamAsClass will throw an exception if the parameter is missing
	public void setCredentials(Context ctx)
	{
		String username = ctx.formParamAsClass("username", String.class).get();
		String password = ctx.formParamAsClass("password", String.class).get();

		Admin admin = adminRepository.readFirst();

		if (admin == null)
		{
			adminRepository.createFirstRunPlaceholder();
			admin = adminRepository.readFirst();
		}
		else if (!admin.alreadyRun())
		{
			ctx.status(400);
			return;
		}

		String passwordHash = argon2Service.hash(password.toCharArray());
		Admin updated = new Admin(admin.id(), username, passwordHash, admin.updatedAt(), false);
		adminRepository.update(admin, updated);

		ctx.status(200);
	}

	public void login(Context ctx)
	{
		String username = ctx.formParamAsClass("username", String.class).get();
		String password = ctx.formParamAsClass("password", String.class).get();

		Admin admin = adminRepository.readFirst();

		if (admin == null || !admin.username().equals(username))
		{
			ctx.status(401);
			ctx.json(new LoginResponse(false));
			return;
		}

		if (!argon2Service.verify(admin.passwordHash(), password.toCharArray()))
		{
			ctx.status(401);
			ctx.json(new LoginResponse(false));
			return;
		}

		ctx.sessionAttribute("admin_logged_in", true);
		ctx.status(200);
		ctx.redirect("/dashboard");
	}
}
