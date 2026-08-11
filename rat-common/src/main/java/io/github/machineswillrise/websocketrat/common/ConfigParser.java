package io.github.machineswillrise.websocketrat.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigParser
{
	private final Properties props;

	public ConfigParser(InputStream stream)
	{
		props = new Properties();

		try
		{
			props.load(stream);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Could not load config file", e);
		}
	}

	public Config parse()
	{
		return new Config(
			props.getProperty("ip"),
			Integer.parseInt(props.getProperty("port"))
		);
	}
}