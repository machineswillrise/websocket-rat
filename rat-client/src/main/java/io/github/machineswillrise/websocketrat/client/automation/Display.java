package io.github.machineswillrise.websocketrat.client.automation;

import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;

import java.awt.event.InputEvent;

import java.awt.image.BufferedImage;

public record Display(GraphicsDevice device, Rectangle bounds, Robot robot, boolean isMaster)
{
	public BufferedImage capture()
	{
		return robot.createScreenCapture(bounds);
	}

	public void moveMouse(int x, int y)
	{
		if (bounds.contains(new Point(x, y)))
		{
			robot.mouseMove(x, y);
		}
	}

	public void clickMouse(MouseButton button)
	{
		switch (button)
		{
			case LEFT ->
			{
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			}

			case MIDDLE ->
			{
				robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
			}

			case RIGHT ->
			{
				robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
			}
		}
	}

	public void scrollMouse(int notches)
	{
		robot.mouseWheel(notches);
	}
}
