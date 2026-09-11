package io.github.machineswillrise.websocketrat.client;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public enum LinuxDistro
{
	ALMALINUX("AlmaLinux", "almalinux"),
	ALPINE("Alpine", "alpine"),
	ARCH("Arch", "arch"),
	ARTIX("Artix", "artix"),
	BAZZITE("Bazzite", "bazzite"),
	CACHYOS("CachyOS", "cachyos"),
	DEBIAN("Debian", "debian"),
	DEVUAN("Devuan", "devuan"),
	ENDEAVOUROS("EndeavourOS", "endeavouros"),
	FEDORA("Fedora", "fedora"),
	GUIX("Guix", "guix"),
	KALI("Kali", "kali"),
	KICKSECURE("Kicksecure", "kicksecure"),
	KODACHI("Kodachi", "kodachi"),
	MANJARO("Manjaro", "manjaro"),
	MINT("Mint", "mint"),
	NIXOS("NixOS", "nixos"),
	OPENMANDRIVA("OpenMandriva", "openmandriva"),
	OPENSUSE("OpenSUSE", "opensuse"),
	PARABOLA("Parabola", "parabola"),
	PARROT("Parrot", "parrot"),
	ROCKY_LINUX("Rocky Linux", "rhel"),
	SECUREBLUE("SecureBlue", "secureblue"),
	TAILS("Tails", "tails"),
	TRISQUEL("Trisquel", "trisquel"),
	UBUNTU("Ubuntu", "ubuntu"),
	VOID("Void", "void"),
	UNKNOWN("Unknown", "unknown");

	private final String displayName, id;

	LinuxDistro(String displayName, String id)
	{
		this.displayName = displayName;
		this.id = id;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public String getId()
	{
		return id;
	}

	public static LinuxDistro detectCurrentLinuxDistro() throws IOException
	{
		try
		{
			Path releaseInfo = Path.of("/etc/os-release");
			String[] lines = Files.readString(releaseInfo).split("\\R");

			int nameLineIndex = -1;
			for (int i = 0; i < lines.length; i++)
			{
				if (lines[i].startsWith("NAME="))
				{
					nameLineIndex = i;
				}
			}

			if (nameLineIndex == -1)
			{
				throw new IOException("Release information did not contain NAME key.");
			}

			String nameLine = lines[nameLineIndex];
			String currentDistro = nameLine.substring(nameLine.indexOf("\"") + 1, nameLine.length());

			for (LinuxDistro distro : values())
			{
				if (currentDistro.contains(distro.getDisplayName()))
				{
					return distro;
				}
			}
		}
		catch (NoSuchFileException e)
		{
			throw new IOException("Could not find release information: ", e);
		}

		return UNKNOWN;
	}
}
