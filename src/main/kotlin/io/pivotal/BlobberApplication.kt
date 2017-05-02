package io.pivotal

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootApplication
class BlobberApplication

fun main(args: Array<String>) {
    SpringApplication.run(BlobberApplication::class.java, *args)
}
