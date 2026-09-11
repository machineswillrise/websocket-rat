package io.github.machineswillrise.websocketrat.client;

public class RatException extends Exception
{
	public RatException(String message)
	{
		super(message);
	}

	public RatException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
