package io.github.machineswillrise.websocketrat.common;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigParserTest
{
	@Test
	@DisplayName("Should return same data when parsing valid configuration")
	void givenValidConfiguration_whenParse_thenReturnSameData()
	{
		String config =
		"""
		foo = 123
		bar = 456
		""";

		var stream = new ByteArrayInputStream(config.getBytes(StandardCharsets.UTF_8));
		var parser = new ConfigParser(stream);

		var expectedOutput = new LinkedHashMap<String, String>();
		expectedOutput.put("foo", "123");
		expectedOutput.put("bar", "456");

		assertEquals(expectedOutput, parser.parse());
	}
}
