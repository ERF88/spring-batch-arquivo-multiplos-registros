package com.github.erf88.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class ArquivoFaturaJob {

	@Bean
	public Job faturaJob(
			JobBuilderFactory jobBuilderFactory,
			Step faturaStep) {

		return jobBuilderFactory
				.get("faturaJob")
				.incrementer(new RunIdIncrementer())
				.start(faturaStep)
				.build();
	}

}
