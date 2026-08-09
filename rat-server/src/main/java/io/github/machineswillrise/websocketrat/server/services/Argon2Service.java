package io.github.machineswillrise.websocketrat.server.services;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Argon2Service
{
	private static final int ITERATIONS = 10;
	private static final int MEMORY = 65536;
	private static final int PARALLELISM = 1;

	private final Argon2 argon2;

	public Argon2Service()
	{
		argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
	}

	public String hash(char[] password)
	{
		return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password);
	}

	public boolean verify(String correctPWHash, char[] incorrectPWHash)
	{
		return argon2.verify(correctPWHash, incorrectPWHash);
	}
}
