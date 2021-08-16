package com.ibm.batch.demo.job;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibm.batch.demo.domain.Bonus;
import com.ibm.batch.demo.domain.Emp;
import com.ibm.batch.demo.processor.BonusCalcProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing // Spring Batch 을 유효화
@RequiredArgsConstructor // Lombok에 의한 생성자 자동 생성
public class SimpleJob {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;
    
    private final SqlSessionFactory sqlSessionFactory;
    
    @Bean
    public MyBatisCursorItemReader empReader() {
        final MyBatisCursorItemReader<Emp> reader = new MyBatisCursorItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("EmpMapper.findAll");
        return reader;
    }
    
    @Bean
    public BonusCalcProcessor processor() {
        return new BonusCalcProcessor();
    }
    
    @Bean
    public MyBatisBatchItemWriter<Bonus> writer() {
        final MyBatisBatchItemWriter<Bonus> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("BonusMapper.insert");
        return writer;
    }  
    
    @Bean
    public JobExecutionListener listener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("before job");
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
            	log.info("after job");
            }
        };
    }
    
    @Bean
    public Job singleJob() {
        return jobBuilderFactory
                .get("SimpleJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(readEmpAndWriteBonus())
                .end()
                .build();
    }
    
    @Bean
    public Step readEmpAndWriteBonus() {
        return stepBuilderFactory
                .get("readEmpAndWriteBonus")
                .<Emp, Bonus>chunk(10)
                .reader(empReader())
                .processor(processor())
                .writer(writer())
                .build();
    }
}
