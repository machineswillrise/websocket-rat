package io.github.machineswillrise.websocketrat.server;

import org.jooq.DSLContext;

import io.github.machineswillrise.websocketrat.server.controllers.AdminController;
import io.github.machineswillrise.websocketrat.server.repositories.AdminRepository;
import io.github.machineswillrise.websocketrat.server.services.AdminService;
import io.github.machineswillrise.websocketrat.server.services.Argon2Service;

/*
 * Repositories and services will be initialized here and then you can fetch the
 * high-level controller from it.
 */
public class DIContainer
{
	public final AdminController adminController;

	public DIContainer(DSLContext context)
	{
		var argon2Service = new Argon2Service();

		var adminRepository = new AdminRepository(context);
		var adminService = new AdminService(adminRepository, argon2Service);
		adminController = new AdminController(adminService);
	}
}
