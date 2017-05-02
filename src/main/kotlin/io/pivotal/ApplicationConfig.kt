package io.pivotal

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.context.annotation.Configuration


@Configuration
class ApplicationConfig : WebMvcConfigurerAdapter() {

    @Autowired lateinit var testInterceptor : TestInterceptor

    override fun addInterceptors(registry: InterceptorRegistry?) {
        registry!!.addInterceptor(testInterceptor).addPathPatterns("/files")
    }
}