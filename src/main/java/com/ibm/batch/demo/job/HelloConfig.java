package com.ibm.batch.demo.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibm.batch.demo.tasklet.MessageTasklet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 배치 구성 클래스
@Slf4j
@Configuration // Bean 정의 클래스라는 것을 명시하는 어노테이션
@EnableBatchProcessing // Spring Batch 을 유효화
@RequiredArgsConstructor // Lombok에 의한 생성자 자동 생성
public class HelloConfig {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  
  @Bean
  public Job fooJob() {
	log.info("fooJob 메서드 실행");
    return jobBuilderFactory.get("test") // 일억성이 되는 임의 잡 이름을 지정
      .incrementer(new RunIdIncrementer())
      .flow(helloStep()) // 실행하는 Step을 지정
      .end()
      .build();
  }


  @Bean
  public Step helloStep() {
    log.info("helloStep 메서드 실행");
    return stepBuilderFactory.get("myHelloStep") // 임의의 스탭 이름을 지정
      .tasklet(new MessageTasklet("Hello!")) // 실행하는 Tasklet을 지정
      .build();
  }

  @Bean
  public Step worldStep() {
	  log.info("worldStep 메서드를 실행");
    return stepBuilderFactory.get("myWorldStep") // 임의의 스탭 이름을 지정
      .tasklet(new MessageTasklet("World!")) // 실행하는 Tasklet을 지정
      .build();
  }
}