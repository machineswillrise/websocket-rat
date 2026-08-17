package io.github.machineswillrise.websocketrat.client.automation;

import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.Robot;

import java.awt.image.BufferedImage;

public record Display(GraphicsDevice device, Rectangle bounds, Robot robot, boolean isMaster)
{
	public BufferedImage capture()
	{
		return robot.createScreenCapture(bounds);
	}
}
