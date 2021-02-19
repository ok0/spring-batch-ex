package kr.co.mustit.batch.job

import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.flow.FlowExecutionStatus
import org.springframework.batch.core.job.flow.JobExecutionDecider
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.random.Random

@Configuration
class DeciderJobConfiguration(
  private val jobBuilderFactory: JobBuilderFactory,
  private val stepBuilderFactory: StepBuilderFactory
) {
  @Bean
  fun deciderJob(): Job
  = jobBuilderFactory.get("deciderJob")
    .start(startStep())
    .next(decider())
    .from(decider())
      .on("ODD")
      .to(oddStep())
    .from(decider())
      .on("EVEN")
      .to(evenStep())
    .end()
    .build()

  @Bean
  fun startStep(): Step
  = stepBuilderFactory.get("startStep")
    .tasklet(fun (stepContribution, stepChunkContext): RepeatStatus {
      println(">>>>>>>> Start!!")
      return RepeatStatus.FINISHED
    })
    .build()

  @Bean
  fun evenStep(): Step
      = stepBuilderFactory.get("evenStep")
    .tasklet(fun (stepContribution, hunkContext): RepeatStatus {
      println(">>>>>>>> 짝수")
      return RepeatStatus.FINISHED
    })
    .build()

  @Bean
  fun oddStep(): Step
      = stepBuilderFactory.get("oddSTep")
    .tasklet(fun (stepContribution, hunkContext): RepeatStatus {
      println(">>>>>>>> 홀수")
      return RepeatStatus.FINISHED
    })
    .build()

  @Bean
  fun decider(): JobExecutionDecider
  = OddDecider()


  class OddDecider: JobExecutionDecider {
    @Override
    override fun decide(
      jobExecution: JobExecution,
      stepExecution: StepExecution?
    ): FlowExecutionStatus {
      val randomNumber = Random.nextInt(50) + 1
      println("램덤숫자: $randomNumber")

      return if (randomNumber % 2 == 0) {
        FlowExecutionStatus("EVEN")
      } else {
        FlowExecutionStatus("ODD")
      }
    }
  }
}