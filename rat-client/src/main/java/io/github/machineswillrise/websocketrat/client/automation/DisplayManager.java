package io.github.machineswillrise.websocketrat.client.automation;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DisplayManager
{
	private final List<Display> displays = new LinkedList<>();

	public DisplayManager() throws AutomationException
	{
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();

		GraphicsDevice[] devices = environment.getScreenDevices();
		GraphicsDevice master = environment.getDefaultScreenDevice();

		for (int i = 0; i < devices.length; i++)
		{
			GraphicsDevice device = devices[i];
			Display display;

			try
			{
				if (device.equals(master))
				{
					display = new Display(
						device,
						device.getDefaultConfiguration().getBounds(),
						new Robot(device),
						true
					);
				}
				else
				{
					display = new Display(
						device,
						device.getDefaultConfiguration().getBounds(),
						new Robot(device),
						false
					);
				}

				displays.add(display);
			}
			catch (AWTException e)
			{
				throw new AutomationException("Could not open display " + i, e);
			}
		}
	}
	
	public List<Display> getAllDisplays()
	{
		return displays;
	}

	// hi, linux foundation, if you are reading this!
	// no. i won't use the word main :)
	public Optional<Display> getMasterDisplay()
	{
		for (Display display : displays)
		{
			if (display.isMaster())
			{
				return Optional.of(display);
			}
		}

		return Optional.empty();
	}
}
