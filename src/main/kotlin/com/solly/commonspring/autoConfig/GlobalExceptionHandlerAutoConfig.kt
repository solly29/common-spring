package com.solly.commonspring.autoConfig

import com.solly.commonspring.advice.GlobalExceptionHandler
import com.solly.commonspring.advice.JpaExceptionHandler
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GlobalExceptionHandlerAutoConfig {

    @Bean
    fun globalExceptionHandler() = GlobalExceptionHandler()

}

@Configuration
@ConditionalOnClass(name = ["javax.persistence.NoResultException"])
class JpaExceptionHandlerAutoConfig {

    @Bean
    fun jpaExceptionHandler() = JpaExceptionHandler()

}