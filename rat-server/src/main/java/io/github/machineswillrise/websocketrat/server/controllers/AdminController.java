package io.github.machineswillrise.websocketrat.server.controllers;

import io.javalin.http.Context;

import io.github.machineswillrise.websocketrat.server.dto.AuthRequest;
import io.github.machineswillrise.websocketrat.server.dto.LoginResponse;
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

	public void setCredentials(Context ctx)
	{
		AuthRequest request = ctx.bodyAsClass(AuthRequest.class);

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

		String passwordHash = argon2Service.hash(request.password().toCharArray());
		Admin updated = new Admin(admin.id(), request.username(), passwordHash, admin.updatedAt(), false);
		adminRepository.update(admin, updated);

		ctx.status(200);
	}

	public void login(Context ctx)
	{
		AuthRequest request;

		try
		{
			request = ctx.bodyAsClass(AuthRequest.class);
		}
		catch (NullPointerException e)
		{
			ctx.status(400);
			return;
		}

		Admin admin = adminRepository.readFirst();

		if (admin == null || !admin.username().equals(request.username()))
		{
			ctx.status(401);
			ctx.json(new LoginResponse(false));
			return;
		}

		if (!argon2Service.verify(admin.passwordHash(), request.password().toCharArray()))
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
