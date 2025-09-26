package com.solly.commonspring.autoConfig

import com.solly.commonspring.aop.LoggingAop
import com.solly.commonspring.properties.CommonLogProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(LoggingAop::class) // LoggingAop가 classpath에 있을 때만
@EnableConfigurationProperties(CommonLogProperties::class)
class CommonLoggingAutoConfiguration {

    @Bean
    fun loggingAop(properties: CommonLogProperties): LoggingAop {
        return LoggingAop(properties)
    }

}