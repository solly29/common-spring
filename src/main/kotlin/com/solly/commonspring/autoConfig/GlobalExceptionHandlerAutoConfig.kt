package com.solly.commonspring.autoConfig

import com.solly.commonspring.advice.GlobalExceptionHandler
import com.solly.commonspring.advice.JpaExceptionHandler
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(GlobalExceptionHandler::class)
class GlobalExceptionHandlerAutoConfig {

    @Bean
    @ConditionalOnMissingBean(name = ["globalExceptionHandler"]) // LoggingAop가 classpath에 있을 때만
    fun globalExceptionHandler() = GlobalExceptionHandler()

}

@Configuration
@ConditionalOnClass(name = ["javax.persistence.NoResultException"])
class JpaExceptionHandlerAutoConfig {

    @Bean
    @ConditionalOnMissingBean(name = ["jpaExceptionHandler"]) // LoggingAop가 classpath에 있을 때만
    fun jpaExceptionHandler() = JpaExceptionHandler()

}