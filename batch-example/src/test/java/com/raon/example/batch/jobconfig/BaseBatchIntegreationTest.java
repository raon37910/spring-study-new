package com.raon.example.batch.jobconfig;

import static org.assertj.core.api.Assertions.*;

import javax.sql.DataSource;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.raon.example.batch.BatchExampleApplication;

@Sql("/sql/schema.sql")
@ActiveProfiles("test")
@SpringBatchTest
@SpringJUnitConfig(classes = BatchExampleApplication.class)
public abstract class BaseBatchIntegreationTest {
	@Autowired
	protected JobLauncherTestUtils jobLauncherTestUtils;

	protected JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	protected static void assertJobCompleted(JobExecution jobExecution) {
		assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
	}
}
