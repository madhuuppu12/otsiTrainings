package org.mvc;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Configuration
public class BatchConfigurtion {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private DataSource dataSource;

	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importCustomerDataToJSON").incrementer(new RunIdIncrementer()).listener(listener)
				.flow(step1).end().build();
	}

	@Bean
	public Step step1(ItemWriter<? super Customer> writer) {
		return stepBuilderFactory.get("step1").<Customer, Customer>chunk(10).reader(databaseItemReader())
				/* .processor(processor()) */

				.writer(writer).taskExecutor(taskExecutor()).build();
	}

	@Bean
	ItemReader<Customer> databaseItemReader() {
		JdbcCursorItemReader<Customer> databaseReader = new JdbcCursorItemReader<>();
		databaseReader.setDataSource(dataSource);
		databaseReader.setSql("select * from tbl_customer");
		databaseReader.setRowMapper(new BeanPropertyRowMapper<>(Customer.class));

		return databaseReader;
	}

	@Bean
	public JsonFileItemWriter<Customer> jsonFileItemWriter() {
		return new JsonFileItemWriterBuilder<Customer>().jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
				.resource(new FileSystemResource("trade.json")).name("tradeJsonFileItemWriter").build();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor("spring_batch");
		asyncTaskExecutor.setConcurrencyLimit(2);
		asyncTaskExecutor.setConcurrencyLimit(1);
		return asyncTaskExecutor;
	}
}
