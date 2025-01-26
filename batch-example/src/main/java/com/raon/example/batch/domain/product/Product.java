package com.raon.example.batch.domain.product;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.raon.example.batch.dto.ProductUploadCsvRow;
import com.raon.example.batch.util.DateTimeUtil;
import com.raon.example.batch.util.RandomUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {
	private String productId;
	private Long sellerId;

	private String category;
	private String productName;

	private LocalDate salesStartDate;
	private LocalDate salesEndDate;
	private String productStatus;
	private String brand;
	private String manufacturer;

	private int salesPrice;
	private int stockQuantity;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static Product from(ProductUploadCsvRow row) {
		return new Product(
			RandomUtils.generateRandomId(),
			row.getSellerId(),
			row.getCategory(),
			row.getProductName(),
			DateTimeUtil.toLocalDate(row.getSalesStartDate()),
			DateTimeUtil.toLocalDate(row.getSalesEndDate()),
			row.getProductStatus(),
			row.getBrand(),
			row.getManufacturer(),
			row.getSalesPrice(),
			row.getStockQuantity(),
			LocalDateTime.now(),
			LocalDateTime.now()
		);
	}
}
