package com.solly.commonspring.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("common.logging")
data class CommonLogProperties(
    val logging: Boolean = true,
    val requestLog: Boolean = true,
    val responseLog: Boolean = true,
)
