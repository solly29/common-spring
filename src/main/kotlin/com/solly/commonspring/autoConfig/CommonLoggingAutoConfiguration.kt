package com.solly.commonspring.autoConfig

import com.solly.commonspring.aop.LoggingAop
import com.solly.commonspring.properties.CommonLogProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(CommonLogProperties::class)
class CommonLoggingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = ["loggingAop"]) // LoggingAop가 classpath에 있을 때만
    fun loggingAop(properties: CommonLogProperties): LoggingAop {
        return LoggingAop(properties)
    }

}