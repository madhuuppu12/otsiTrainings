package org.mvc;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
//@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	/*
	 * @Bean public FlatFileItemReader<Customer> reader() {
	 * 
	 * System.out.println("RUN");
	 * 
	 * FlatFileItemReader<Customer> customer = new
	 * FlatFileItemReaderBuilder<Customer>().name("customerItemReader")
	 * .resource(new
	 * ClassPathResource("D:\\jyoti\\BATCH_Process\\test.csv")).delimited()
	 * .names(new String[] { "id", "address", "name", "rest_id", "status" })
	 * .fieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() { {
	 * setTargetType(Customer.class); } }).build(); return customer; }
	 */

	/*
	 * @Bean ItemReader<Customer> csvFileItemReader() { FlatFileItemReader<Customer>
	 * csvFileReader = new FlatFileItemReader<>(); //csvFileReader.setResource(new
	 * UrlResource("D:\\jyoti\\BATCH_Process\\test.csv"));
	 * csvFileReader.setResource(new FileSystemResource(new
	 * File("D:\\jyoti\\BATCH_Process\\test.csv")));
	 * csvFileReader.setLinesToSkip(1);
	 * 
	 * LineMapper<Customer> studentLineMapper = createStudentLineMapper();
	 * csvFileReader.setLineMapper(studentLineMapper);
	 * 
	 * return csvFileReader; }
	 * 
	 * private LineMapper<Customer> createStudentLineMapper() {
	 * DefaultLineMapper<Customer> studentLineMapper = new DefaultLineMapper<>();
	 * 
	 * LineTokenizer studentLineTokenizer = createStudentLineTokenizer();
	 * studentLineMapper.setLineTokenizer(studentLineTokenizer);
	 * 
	 * FieldSetMapper<Customer> studentInformationMapper =
	 * createStudentInformationMapper();
	 * studentLineMapper.setFieldSetMapper(studentInformationMapper);
	 * 
	 * return studentLineMapper; }
	 * 
	 * private LineTokenizer createStudentLineTokenizer() { DelimitedLineTokenizer
	 * studentLineTokenizer = new DelimitedLineTokenizer();
	 * studentLineTokenizer.setDelimiter(","); studentLineTokenizer.setNames(new
	 * String[]{"id", "address", "name", "rest_id", "status"}); return
	 * studentLineTokenizer; }
	 * 
	 * private FieldSetMapper<Customer> createStudentInformationMapper() {
	 * BeanWrapperFieldSetMapper<Customer> studentInformationMapper = new
	 * BeanWrapperFieldSetMapper<>();
	 * studentInformationMapper.setTargetType(Customer.class); return
	 * studentInformationMapper; }
	 */
	@Bean
	ItemReader<Customer> csvFileItemReader() {

		// Create reader instance
		FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();

		// Set input file location
		reader.setResource(new FileSystemResource("D:\\jyoti\\BATCH_Process\\customer.csv"));

		// Set number of lines to skips. Use it if file has header rows.
		reader.setLinesToSkip(1);

		// Configure how each line will be parsed and mapped to different values
		reader.setLineMapper(new DefaultLineMapper() {
			{
				// 3 columns in each row
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "id", "address", "name", "rest_id", "status" });
					}
				});
				// Set values in Employee class
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() {
					{
						setTargetType(Customer.class);
					}
				});
			}
		});

		return reader;
	}

	@Bean
	public CutomerItemProcessor processor() {
		return new CutomerItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<CustomerAfterMap> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<CustomerAfterMap>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO batch_process (id, address,name,rest_id,status) VALUES (:id, :address, :name, :rest_id, :status)")
				.dataSource(dataSource).build();
	}

	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importUserJob_SANJA").incrementer(new RunIdIncrementer()).listener(listener)
				.flow(step1).end().build();
	}

	@Bean
	public Step step1(ItemWriter<? super CustomerAfterMap> writer) {
		return stepBuilderFactory.get("step1").<Customer, CustomerAfterMap>chunk(10).reader(csvFileItemReader())
				.processor(processor()).writer(writer).build();
	}

	@Bean
	public Tasklet tasklet() {
		return (contribution, chunkContext) -> {
			return RepeatStatus.FINISHED;
		};
	}
}
