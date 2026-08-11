package io.github.machineswillrise.websocketrat.server.repositories;

import java.util.List;

public interface Repository<T> {
	int count();
	List<T> readAll();
	void update(T oldRow, T newRow);
}
