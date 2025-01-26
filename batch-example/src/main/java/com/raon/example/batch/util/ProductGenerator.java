package com.raon.example.batch.util;

import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.raon.example.batch.domain.product.ProductStatus;
import com.raon.example.batch.dto.ProductUploadCsvRow;

import lombok.SneakyThrows;

// 임시로 쓸 프로젝트! 보통은 스크립트로 만들어주자!
public class ProductGenerator {

	private static final Random random = new Random();

	@SneakyThrows
	public static void main(String[] args) {
		String csvFilePath = "batch-example/data/random_product.csv";
		int recordCount = 10_000_000; // 천만

		try (
			FileWriter fileWriter = new FileWriter(csvFilePath);
			CSVPrinter printer = new CSVPrinter(fileWriter,
				CSVFormat.DEFAULT.builder()
					.setHeader(ReflectionUtils.getFieldNames(ProductUploadCsvRow.class).toArray(String[]::new))
					.build())
		) {
			for (int i = 0; i < recordCount; i++) {
				printer.printRecord(generateRecord());
				if (i % 100000 == 0) {
					System.out.println("Generated " + i + " records.");
				}
			}
		}
	}

	private static Object[] generateRecord() {
		ProductUploadCsvRow row = randomProductRow();
		return new Object[] {
			row.getSellerId(),
			row.getCategory(),
			row.getProductName(),
			row.getSalesStartDate(),
			row.getSalesEndDate(),
			row.getProductStatus(),
			row.getBrand(),
			row.getManufacturer(),
			row.getSalesPrice(),
			row.getStockQuantity()
		};
	}

	private static ProductUploadCsvRow randomProductRow() {
		String[] CATEGORIES = {"가전", "가구", "패션", "식품", "화장품", "서적", "스포츠", "완구", "음악", "디지털"};
		String[] PRODUCT_NAMES = {"TV", "소파", "셔츠", "햇반", "스킨케어세트", "소설", "축구공", "레고", "기타", "스마트폰"};
		String[] BRANDS = {"삼성", "LG", "나이키", "아모레서피", "현대", "BMW", "롯데", "스타벅스", "도미노", "맥도날드"};
		String[] MANUFACTURERS = {"삼성전자", "LG전자", "나이키코리아", "아모레서피", "현대자동차", "BMW코리아", "롯데제과", "스타벅스코리아", "도미노피자",
			"맥도날드코리아"};
		String[] STATUS = Arrays.stream(ProductStatus.values()).map(ProductStatus::name).toArray(String[]::new);
		return ProductUploadCsvRow.of(
			randomSellerId(),
			randomChoice(CATEGORIES),
			randomChoice(PRODUCT_NAMES),
			randomDate(2020, 2023),
			randomDate(2024, 2026),
			randomChoice(STATUS),
			randomChoice(BRANDS),
			randomChoice(MANUFACTURERS),
			randomSalesPrice(),
			randomStockQuantity()
		);
	}

	private static int randomStockQuantity() {
		return random.nextInt(1, 1001);
	}

	private static int randomSalesPrice() {
		return random.nextInt(10_000, 500_001);
	}

	private static String randomDate(int startYear, int endYear) {
		int year = random.nextInt(startYear, endYear + 1);
		int month = random.nextInt(1, 13);
		int day = random.nextInt(1, 29);

		return LocalDate.of(year, month, day).toString();
	}

	private static String randomChoice(String[] arr) {
		return arr[random.nextInt(arr.length)];
	}

	private static Long randomSellerId() {
		return random.nextLong(1, 101);
	}
}
