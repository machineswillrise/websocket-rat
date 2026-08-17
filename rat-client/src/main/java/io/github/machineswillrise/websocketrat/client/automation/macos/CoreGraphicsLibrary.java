package io.github.machineswillrise.websocketrat.client.automation.macos;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface CoreGraphicsLibrary extends Library
{
	CoreGraphicsLibrary INSTANCE = Native.load("CoreGraphics", CoreGraphicsLibrary.class);

	boolean CGPreflightScreenCaptureAccess();
	boolean CGRequestScreenCaptureAccess();
}
