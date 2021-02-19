package kr.co.mustit.batch.job

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StepNextJobConfiguration(
  private val jobBuilderFactory: JobBuilderFactory,
  private val stepBuilderFactory: StepBuilderFactory
) {
  @Bean
  fun stepNextJob(): Job
  = jobBuilderFactory.get("stepNextJob")
    .start(step1())
    .next(step2())
    .next(step3())
    .build()

  @Bean
  fun step1(): Step
  = stepBuilderFactory.get("step1")
    .tasklet(fun (_, _): RepeatStatus {
      println(">>>>>>>> This is Step1")
      return RepeatStatus.FINISHED
    })
    .build()

  @Bean
  fun step2(): Step
      = stepBuilderFactory.get("step2")
    .tasklet(fun (_, _): RepeatStatus {
      println(">>>>>>>> This is Step2")
      return RepeatStatus.FINISHED
    })
    .build()

  @Bean
  fun step3(): Step
      = stepBuilderFactory.get("step3")
    .tasklet(fun (_, _): RepeatStatus {
      println(">>>>>>>> This is Step3")
      return RepeatStatus.FINISHED
    })
    .build()
}