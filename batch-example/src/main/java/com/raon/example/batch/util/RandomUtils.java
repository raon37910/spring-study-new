package com.raon.example.batch.util;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class RandomUtils {

	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static String generateRandomId() {
		return Instant.now().toEpochMilli() + "_" + UUID.randomUUID();
	}
}
