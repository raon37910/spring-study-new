package com.raon.example.batch.jobconfig.product.upload;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.raon.example.batch.domain.product.Product;
import com.raon.example.batch.dto.ProductUploadCsvRow;
import com.raon.example.batch.util.ReflectionUtils;

@Configuration
public class ProductUploadJobConfiguration {

	@Bean
	public Job productUploadJob(
		JobRepository jobRepository,
		Step productUploadStep,
		JobExecutionListener jobExecutionListener
	) {
		return new JobBuilder("productUploadJob", jobRepository)
			.start(productUploadStep)
			.build();
	}

	@Bean
	public Step productUploadStep(
		JobRepository jobRepository,
		PlatformTransactionManager transactionManager,
		StepExecutionListener stepExecutionListener,
		ItemReader<ProductUploadCsvRow> productReader,
		ItemProcessor<ProductUploadCsvRow, Product> productProcessor,
		ItemWriter<Product> productWriter
	) {
		return new StepBuilder("productUploadStep", jobRepository)
			.<ProductUploadCsvRow, Product>chunk(1000, transactionManager)
			.allowStartIfComplete(true)
			.reader(productReader)
			.processor(productProcessor)
			.writer(productWriter)
			.listener(stepExecutionListener)
			.build();
	}

	@Bean
	@StepScope
	public FlatFileItemReader<ProductUploadCsvRow> productReader(
		@Value("#{jobParameters['inputFilePath']}") String path
	) {
		return new FlatFileItemReaderBuilder<ProductUploadCsvRow>()
			.name("productReader")
			.resource(new FileSystemResource(path))
			.delimited()
			.names(ReflectionUtils.getFieldNames(ProductUploadCsvRow.class).toArray(String[]::new))
			.targetType(ProductUploadCsvRow.class)
			.linesToSkip(1)
			.build();
	}

	@Bean
	public ItemProcessor<ProductUploadCsvRow, Product> productProcessor() {
		return Product::from;
	}

	@Bean
	public JdbcBatchItemWriter<Product> productWriter(DataSource dataSource) {
		String sql = "insert into products(product_id, seller_id, category, product_name,"
			+ "sales_start_date, sales_end_date, product_status, brand, manufacturer, sales_price,"
			+ "stock_quantity, created_at, updated_at) values(:productId, :sellerId, :category, :productName,"
			+ ":salesStartDate, :salesEndDate, :productStatus, :brand, :manufacturer, :salesPrice,"
			+ ":stockQuantity, :createdAt, :updatedAt)";

		return new JdbcBatchItemWriterBuilder<Product>()
			.dataSource(dataSource)
			.sql(sql)
			.beanMapped()
			.build();
	}
}
