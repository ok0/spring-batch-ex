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
class SimpleJobConfiguration(
  private val jobBuilderFactory: JobBuilderFactory,
  private val stepBuilderFactory: StepBuilderFactory
) {
  @Bean
  fun simpleJob(): Job
  = jobBuilderFactory.get("simpleJob")
    .start(simpleStep1(null))
    .next(simpleStep2())
    .build()

  @Bean
  @JobScope
  fun simpleStep1(
    @Value("#{jobParameters[requestDate]}") requestDate: String?
  ): Step
  = stepBuilderFactory.get("simpleStep1")
    .tasklet(fun (_, _): RepeatStatus {
      println(">>>>>> This is Step1")
      println(">>>>>> requestDate = {$requestDate}")
      return RepeatStatus.FINISHED
    })
    .build()

  @Bean
  @JobScope
  fun simpleStep2(): Step
  = stepBuilderFactory.get("simpleStep2")
    .tasklet(fun(_, _): RepeatStatus {
      throw IllegalArgumentException()
    })
    .build()

  @Bean
  fun moreSimpleJob1(): Job
  = jobBuilderFactory.get("moreSimpleStep1")
    .start(moreSimpleStep1())
    .build()

  @Bean
  fun moreSimpleStep1(): Step
  = stepBuilderFactory.get("moreSimpleStep1")
    .tasklet(fun (_, _): RepeatStatus {
      println(">>>>>>> This is MoreSimpleStep1")
      return RepeatStatus.FINISHED
    })
    .build()
}