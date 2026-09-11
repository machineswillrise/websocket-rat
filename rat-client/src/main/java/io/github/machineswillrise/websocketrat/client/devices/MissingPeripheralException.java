package io.github.machineswillrise.websocketrat.client.devices;

import io.github.machineswillrise.websocketrat.client.RatException;

public class MissingPeripheralException extends RatException
{
	public MissingPeripheralException(String peripheral)
	{
		super("A peripheral was missing: " + peripheral);
	}

	public MissingPeripheralException(String peripheral, Throwable cause)
	{
		super("A peripheral was missing: " + peripheral, cause);
	}
}
