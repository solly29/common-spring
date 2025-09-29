package com.solly.commonspring.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "tomcat.ajp")
data class AjpConfigProperties(
    var enable: Boolean = false,
    var port: Int = 8009,
    var protocol: String = "AJP/1.3",
)
