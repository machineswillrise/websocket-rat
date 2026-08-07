package io.github.machineswillrise.websocketrat.server.services;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import io.github.machineswillrise.websocketrat.server.repositories.AdminRepository;

public class AdminService
{
	private static final int ITERATIONS = 10;
	private static final int MEMORY = 65536;
	private static final int PARALLELISM = 1;

	private final AdminRepository adminRepository;
	private final Argon2 argon2;

	public AdminService(AdminRepository adminRepository)
	{
		this.adminRepository = adminRepository;
		argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
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

	public void setCredentials(String username, String password)
	{
		if (username == null || password == null)
			throw new IllegalArgumentException("Username and password are required");

		if (username.length() > 32 || username.length() < 8 || password.length() > 32 || password.length() < 8)
			throw new IllegalArgumentException("Username and password lengths must be between 8 and 32 characters");

		if (!canSetCredentials())
			throw new IllegalArgumentException("The admin account is already set up.");

		String hash = argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password.toCharArray());

		adminRepository.updateCredentials(username, hash);
	}
}
