package kr.co.mustit.batch.listener

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.listener.StepExecutionListenerSupport

class SkipCheckingListener(): StepExecutionListenerSupport() {

  @Override
  override fun afterStep(stepExecution: StepExecution): ExitStatus?
  = if (stepExecution.exitStatus.exitCode == ExitStatus.FAILED.exitCode
    && stepExecution.skipCount > 0) {
      ExitStatus("COMPLETED WITH SKIPS")
    } else {
      null
    }
}