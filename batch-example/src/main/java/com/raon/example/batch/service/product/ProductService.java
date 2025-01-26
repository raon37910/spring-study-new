package com.raon.example.batch.service.product;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final JdbcTemplate jdbcTemplate;

	public Long countProducts() {
		return jdbcTemplate.queryForObject("select count(*) from products", Long.class);
	}
}
