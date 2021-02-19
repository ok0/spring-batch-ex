package kr.co.mustit.batch.job

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ScopeJobConfiguration(
  private val jobBuilderFactory: JobBuilderFactory,
  private val stepBuilderFactory: StepBuilderFactory
) {
  @Bean
  fun scopeJob(): Job
  = jobBuilderFactory.get("scopeJob")
    .start(scopeStep1(null))
    .next(scopeStep2())
    .build()

  @Bean
  @JobScope
  fun scopeStep1(
    @Value("#{jobParameters[requestDate]}") requestDate: String?
  ): Step
  = stepBuilderFactory.get("scopeStep1")
    .tasklet(fun (stepContribution, stepChunkContext): RepeatStatus {
      println(">>>>>>> This is scopeStep1")
      println(">>>>>>> requestDate = $requestDate")
      return RepeatStatus.FINISHED
    })
    .build()

  @Bean
  fun scopeStep2(): Step
  = stepBuilderFactory.get("scopeStep2")
    .tasklet(scopeStep2Tasklet(null))
    .build()

  @Bean
  @StepScope
  fun scopeStep2Tasklet(
    @Value("#{jobParameters[requestDate]}") requestDate: String?
  ): (StepContribution, ChunkContext) -> RepeatStatus
  = fun (stepContribution, chunkContext): RepeatStatus {
    println(">>>>>> This is scopeStep2")
    println(">>>>>>> requestDate = $requestDate")
    return RepeatStatus.FINISHED
  }
}