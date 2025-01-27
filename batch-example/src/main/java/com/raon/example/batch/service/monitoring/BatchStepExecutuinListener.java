package com.raon.example.batch.service.monitoring;

import java.util.Map;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchStepExecutuinListener implements StepExecutionListener {

	private final CustomPrometheusPushGatewayManager manager;

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("after step - execution context: {}", stepExecution.getExecutionContext());
		manager.pushMetrics(
			Map.of("job_name", stepExecution.getJobExecution().getJobInstance().getJobName()));
		return ExitStatus.COMPLETED;
	}

}
