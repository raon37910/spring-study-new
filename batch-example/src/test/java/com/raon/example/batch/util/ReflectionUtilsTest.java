package com.raon.example.batch.util;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReflectionUtilsTest {
	private static class TestClass {
		private String stringField;
		private int intField;
		public static final String CONSTANT = "const";
	}

	@Test
	@DisplayName("static field는 포함되지 않으며 나머지 모든 필드는 포함 된다.")
	void testGetFieldNames() throws Exception {
		// given
		// when
		List<String> fieldNames = ReflectionUtils.getFieldNames(TestClass.class);
		// then
		assertThat(fieldNames).hasSize(2)
			.containsExactly("stringField", "intField")
			.doesNotContain("const");

	}
}