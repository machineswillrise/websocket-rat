package io.github.machineswillrise.websocketrat.client.devices;

public class MissingPeripheralException extends Exception
{
	public MissingPeripheralException(String message)
	{
		super(message);
	}

	public MissingPeripheralException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
