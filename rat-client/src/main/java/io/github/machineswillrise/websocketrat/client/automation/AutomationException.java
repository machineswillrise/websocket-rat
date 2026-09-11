package io.github.machineswillrise.websocketrat.client.automation;

import io.github.machineswillrise.websocketrat.client.RatException;

public class AutomationException extends RatException
{
	public AutomationException(String message)
	{
		super(message);
	}

	public AutomationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
