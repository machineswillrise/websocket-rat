package io.github.machineswillrise.websocketrat.common;

public record Config(String ip, int port)
{
	public Config(String ip, int port)
	{
		for (String s : ip.split("\\."))
		{
			try
			{
				int octet = Integer.parseInt(s);
				if (octet > 255 || octet < 0)
				{
					throw new IllegalArgumentException("Octet exceeds range: " + octet);
				}
			}
			catch (NumberFormatException e)
			{
				throw new IllegalArgumentException("Octet contains invalid characters: " + s);
			}
		}

		if (port > 65535 || port < 0)
		{
			throw new IllegalArgumentException("Port exceeds range: " + port);
		}

		this.ip = ip;
		this.port = port;
	}
}
