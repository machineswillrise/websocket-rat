package io.github.machineswillrise.websocketrat.server.services;

import io.github.machineswillrise.websocketrat.server.dto.AdminCredentials;
import io.github.machineswillrise.websocketrat.server.repositories.AdminRepository;

public class AdminService
{
	private final AdminRepository adminRepository;
	private final Argon2Service argon2Service;

	public AdminService(AdminRepository adminRepository, Argon2Service argon2Service)
	{
		this.adminRepository = adminRepository;
		this.argon2Service = argon2Service;
	}

	private boolean canSetCredentials()
	{
		if (adminRepository.isEmpty())
		{
			adminRepository.createFirstRunPlaceholder();
			return true;
		}

		return adminRepository.isFirstRun();
	}

	public void setCredentials(String username, char[] password)
	{
		if (!canSetCredentials())
		{
			throw new IllegalArgumentException("The admin account is already set up.");
		}

		adminRepository.updateCredentials(username, argon2Service.hash(password));
	}

	public boolean verifyCredentials(String username, char[] password)
	{
		AdminCredentials credentials = adminRepository.findCredentials();

		if (credentials == null || !credentials.username().equals(username))
		{
			return false;
		}

		return argon2Service.verify(credentials.passwordHash(), password);
	}
}
