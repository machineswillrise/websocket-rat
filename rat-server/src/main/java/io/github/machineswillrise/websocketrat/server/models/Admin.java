package io.github.machineswillrise.websocketrat.server.models;

import java.time.LocalDateTime;

public record Admin(
	int id,
	String username,
	String passwordHash,
	LocalDateTime updatedAt,
	boolean alreadyRun
)
{
}
