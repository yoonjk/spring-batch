package com.ibm.batch.demo.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// Tasklet 실행 클래스
@Slf4j
@RequiredArgsConstructor // Lombok에 의한 생성자 자동생성
public class MessageTasklet implements Tasklet {

  // 출력 메세지
  private final String message;

  // 구체적인 처리을 코딩하는 메서드
  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    log.info("Message: "+message); // 메세지 출력
    return RepeatStatus.FINISHED; // 처리가 완료된 것을 나타내는 수치 반환
  }
}
