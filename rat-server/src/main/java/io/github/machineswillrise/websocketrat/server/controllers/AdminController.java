package io.github.machineswillrise.websocketrat.server.controllers;

import java.util.Map;

import io.javalin.http.Context;

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
			ctx.render(
				"error.jte",
				Map.of(
					"error",
					"Credentials have already been set up."
				)
			);
			return;
		}

		String passwordHash = argon2Service.hash(password.toCharArray());
		Admin updated = new Admin(admin.id(), username, passwordHash, admin.updatedAt(), false);
		adminRepository.update(admin, updated);

		ctx.redirect("/success");
	}

	public void login(Context ctx)
	{
		String username = ctx.formParamAsClass("username", String.class).get();
		String password = ctx.formParamAsClass("password", String.class).get();

		Admin admin = adminRepository.readFirst();

		if (admin == null || !admin.username().equals(username))
		{
			ctx.status(403);
			ctx.render(
				"error.jte",
				Map.of(
					"error",
					"You have entered the wrong username or the administrator account has not been set up."
				)
			);

			return;
		}

		if (!argon2Service.verify(admin.passwordHash(), password.toCharArray()))
		{
			ctx.status(403);
			ctx.render(
				"error.jte",
				Map.of(
					"error",
					"You have entered the wrong password."
				)
			);

			return;
		}

		ctx.sessionAttribute("admin_logged_in", true);
		ctx.redirect("/dashboard");
	}

	public void loadDashboard(Context ctx)
	{
		if (ctx.sessionAttribute("admin_logged_in") == null)
		{
			ctx.status(403);
			ctx.render(
				"error.jte",
				Map.of(
					"error",
					"You are not authorized to view this page."
				)
			);

			return;
		}

		ctx.render("dashboard.jte");
	}
}
