package io.github.machineswillrise.websocketrat.common;

import java.io.InputStream;
import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Properties;

public class ConfigParser
{
	private Properties props;

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

	public LinkedHashMap<String, String> parse()
	{
		var map = new LinkedHashMap<String, String>();
		for (String key : props.stringPropertyNames()) {
			map.put(key, props.getProperty(key));
		}

		return map;
	}
}