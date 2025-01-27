package com.raon.example.batch.service.monitoring;

import java.util.Map;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchJobExecutionListener implements JobExecutionListener {

	private final CustomPrometheusPushGatewayManager manager;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("listener: before Job");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		log.info("listener: after Job {}", jobExecution.getStatus());
		manager.pushMetrics(Map.of("job_name", jobExecution.getJobInstance().getJobName()));
	}
}
