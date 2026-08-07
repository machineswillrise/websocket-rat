package io.github.machineswillrise.websocketrat.server.controllers;

import io.javalin.http.Context;

import io.github.machineswillrise.websocketrat.server.dto.AuthRequest;
import io.github.machineswillrise.websocketrat.server.dto.LoginResponse;
import io.github.machineswillrise.websocketrat.server.services.AdminService;

public class AdminController
{
	private final AdminService adminService;

	public AdminController(AdminService adminService)
	{
		this.adminService = adminService;
	}

	public void setCredentials(Context ctx)
	{
		AuthRequest request = ctx.bodyAsClass(AuthRequest.class);

		try
		{
			adminService.setCredentials(request.username(), request.password());
		}
		catch (IllegalArgumentException e)
		{
			ctx.status(400);
			return;
		}

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

		if (!adminService.verifyCredentials(request.username(), request.password()))
		{
			ctx.status(401);
			ctx.json(new LoginResponse(false));
			return;
		}

		ctx.sessionAttribute("admin_logged_in", true);
		ctx.status(200);
		ctx.json(new LoginResponse(true));
	}
}
