package io.github.machineswillrise.websocketrat.client.automation;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.awt.GraphicsDevice;
import java.awt.image.BufferedImage;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public record Display(GraphicsDevice device, Rectangle bounds, Robot robot, boolean isMaster)
{
	public byte[] capture() throws IOException
	{
		BufferedImage screenshot = robot.createScreenCapture(bounds);
		var baos = new ByteArrayOutputStream();

		ImageIO.write(screenshot, "png", baos);
		return baos.toByteArray();
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

	public void type(char c)
	{
		int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
		boolean isUpperCase = Character.isUpperCase(c);

		if (keyCode == KeyEvent.VK_UNDEFINED)
		{
			throw new IllegalArgumentException("Unknown key code for key " + c);
		}

		if (isUpperCase)
		{
			robot.keyPress(KeyEvent.VK_SHIFT);
		}

		robot.keyPress(keyCode);
		robot.keyRelease(keyCode);

		if (isUpperCase)
		{
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}

		robot.delay(50);
	}
}
