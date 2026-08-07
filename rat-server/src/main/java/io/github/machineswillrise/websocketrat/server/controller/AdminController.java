package io.github.machineswillrise.websocketrat.server.controller;

import io.javalin.http.Context;

import io.github.machineswillrise.websocketrat.server.service.AdminService;

public class AdminController
{
	private final AdminService adminService;

	public AdminController(AdminService adminService)
	{
		this.adminService = adminService;
	}

	public void setCredentials(Context ctx)
	{
		String username = ctx.queryParam("username");
		String password = ctx.queryParam("password");

		try
		{
			adminService.setCredentials(username, password);
		}
		catch (IllegalArgumentException e)
		{
			ctx.status(400);
			return;
		}

		ctx.status(200);
	}
}
