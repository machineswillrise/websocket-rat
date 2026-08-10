package io.github.machineswillrise.websocketrat.common;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConfigParserTest
{
	@Test
	@DisplayName("Should return same data when parsing valid configuration")
	void givenValidConfiguration_whenParse_thenReturnSameData()
	{
		String config =
		"""
		ip = 9.9.9.9
		port = 1234
		""";

		Config expectedResult = new Config("9.9.9.9", 1234);

		var stream = new ByteArrayInputStream(config.getBytes(StandardCharsets.UTF_8));
		var parser = new ConfigParser(stream);

		assertEquals(expectedResult, parser.parse());
	}
}
