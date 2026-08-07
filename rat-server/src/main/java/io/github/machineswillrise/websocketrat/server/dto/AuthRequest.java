package io.github.machineswillrise.websocketrat.server.dto;

import java.util.Objects;

public record AuthRequest(String username, String password)
{
	public AuthRequest
	{
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
	}
}
