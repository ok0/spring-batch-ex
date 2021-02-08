package kr.co.mustit.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class BatchApplication

fun main(args: Array<String>) {
	runApplication<BatchApplication>(*args)
}
