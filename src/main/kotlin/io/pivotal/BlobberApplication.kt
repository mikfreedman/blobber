package io.pivotal

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class BlobberApplication

fun main(args: Array<String>) {
    SpringApplication.run(BlobberApplication::class.java, *args)
}
