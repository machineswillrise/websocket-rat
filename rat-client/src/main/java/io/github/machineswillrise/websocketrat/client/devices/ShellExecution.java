package io.github.machineswillrise.websocketrat.client.devices;

public record ShellExecution(
	// if the privilege excalation failed, it won't be done
	boolean done,
	int exitCode,
	String stdout,
	String stderr
) {}

