package io.github.machineswillrise.websocketrat.client.automation;

public class AutomationException extends Exception
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
