package com.raon.example.batch.jobconfig.product.upload;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;

import com.raon.example.batch.jobconfig.BaseBatchIntegreationTest;
import com.raon.example.batch.service.product.ProductService;

@TestPropertySource(properties = {"spring.batch.job.name=productUploadJob"})
class ProductUploadJobConfigurationTest extends BaseBatchIntegreationTest {

	@Autowired
	private ProductService productService;

	@Value("classpath:/data/products_for_upload.csv")
	private Resource input;

	@Test
	void test_job(@Autowired Job productUploadJob) throws Exception {
		JobParameters jobParameters = jobParameters();
		jobLauncherTestUtils.setJob(productUploadJob);

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

		Long productsCount = productService.countProducts();

		assertAll(
			() -> assertThat(productsCount).isEqualTo(6),
			() -> assertJobCompleted(jobExecution)
		);
	}

	private JobParameters jobParameters() throws Exception {
		return new JobParametersBuilder()
			.addJobParameter("inputFilePath", new JobParameter<>(input.getFile().getPath(), String.class, false))
			.toJobParameters();
	}
}