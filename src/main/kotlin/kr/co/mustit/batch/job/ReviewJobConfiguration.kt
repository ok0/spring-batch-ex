package kr.co.mustit.batch.job

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ReviewJobConfiguration(
  private val jobBuilderFactory: JobBuilderFactory,
  private val stepBuilderFactory: StepBuilderFactory
) {
  @Bean
  fun reviewJob(): Job
  = jobBuilderFactory.get("reviewJob")
    .start(reviewStep1(null))
    .next(reviewStep2(null))
    .build()


  @Bean
  @JobScope
  fun reviewStep1(
    @Value("#{jobParameters[requestDate]}") requestDate: String?
  ): Step
  = stepBuilderFactory.get("reviewStep1")
    .tasklet(fun (stepContribution, chunkContext): RepeatStatus {
      println(">>>>>>>>>>> This is reviewStep1")
      println(">>>>>>>>>>> requestDate = $requestDate")
      return RepeatStatus.FINISHED
    })
    .build()

  @Bean
  @JobScope
  fun reviewStep2(
    @Value("#{jobParameters[requestDate]}") requestDate: String?
  ): Step
  = stepBuilderFactory.get("reviewStep2")
    .tasklet(fun (stepContribution, chunkContext): RepeatStatus {
      println(">>>>>>>>>>> This is reviewStep2")
      println(">>>>>>>>>>> requestDate = $requestDate")
      return RepeatStatus.FINISHED
    })
    .build()
}