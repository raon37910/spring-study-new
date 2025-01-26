package com.raon.example.batch.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static LocalDate toLocalDate(String date) {
		return LocalDate.parse(date, FORMATTER);
	}
}
