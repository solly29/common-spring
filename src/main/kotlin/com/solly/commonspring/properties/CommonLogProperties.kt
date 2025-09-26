package com.solly.commonspring.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "common.logging")
data class CommonLogProperties(
    var logging: Boolean = true,
    var requestLog: Boolean = true,
    var responseLog: Boolean = true,
)
