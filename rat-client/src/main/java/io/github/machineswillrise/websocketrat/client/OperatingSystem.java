package io.github.machineswillrise.websocketrat.client;

public enum OperatingSystem
{
	WINDOWS_10("Windows 10"),
	WINDOWS_11("Windows 11"),

	GNU_LINUX("GNU/Linux"),
	MAC_OS("MacOS"),

	UNKNOWN("Unknown");

	private final String displayName;

	OperatingSystem(String displayName)
	{
		this.displayName = displayName;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public static OperatingSystem detectCurrentOperatingSystem()
	{
		String operatingSystem = System.getProperty("os.name").toLowerCase();

		// JAVA 21 can't run on Windows versions older than 10
		if (operatingSystem.contains("win"))
		{
			if (operatingSystem.contains("11"))
			{
				return WINDOWS_11;
			}
			else
			{
				return WINDOWS_10;
			}
		}

		if (operatingSystem.contains("linux"))
		{
			return GNU_LINUX;
		}

		if (operatingSystem.contains("mac"))
		{
			return MAC_OS;
		}

		return UNKNOWN;
	}
}
