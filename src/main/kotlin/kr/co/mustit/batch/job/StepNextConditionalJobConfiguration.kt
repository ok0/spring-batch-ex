package kr.co.mustit.batch.job

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StepNextConditionalJobConfiguration(
  private val jobBuilderFactory: JobBuilderFactory,
  private val stepBuilderFactory: StepBuilderFactory
) {
  @Bean
  fun stepNextConditionalJob(): Job
  = jobBuilderFactory.get("stepNextConditionalJob")
    .start(conditionalJobStep1())
      .on("FAILED")
      .to(conditionalJobStep3())
      .on("*")
      .end()
    .from(conditionalJobStep1())
      .on("*")
      .to(conditionalJobStep2())
      .next(conditionalJobStep3())
      .on("*")
      .end()
    .end()
    .build()

  @Bean
  fun conditionalJobStep1(): Step
  = stepBuilderFactory.get("conditionalJobStep1")
    .tasklet(fun (stepContribution, _): RepeatStatus {
      println(">>>>>>>>>> This is stepNextConditionalJob Step1")
      stepContribution.exitStatus = ExitStatus.FAILED
      return RepeatStatus.FINISHED
    })
    .build()

  @Bean
  fun conditionalJobStep2(): Step
      = stepBuilderFactory.get("conditionalJobStep2")
    .tasklet(fun (_, _): RepeatStatus {
      println(">>>>>>>>>> This is stepNextConditionalJob Step2")
      return RepeatStatus.FINISHED
    })
    .build()

  @Bean
  fun conditionalJobStep3(): Step
      = stepBuilderFactory.get("conditionalJobStep3")
    .tasklet(fun (_, _): RepeatStatus {
      println(">>>>>>>>>> This is stepNextConditionalJob Step3")
      return RepeatStatus.FINISHED
    })
    .build()
}