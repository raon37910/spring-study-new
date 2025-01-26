package com.raon.example.batch.util;

import static java.lang.reflect.Modifier.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {
	public static List<String> getFieldNames(Class<?> clazz) {
		List<String> fieldsNames = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			if (!isStatic(field.getModifiers())) {
				fieldsNames.add(field.getName());
			}
		}

		return fieldsNames;
	}
}
