package com.solly.commonspring.properties

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "multi-datasource")
data class MultiDbProperties(
    val config: Map<String, DbConfig>
)

data class DbConfig(
    val type: DaoType,
    val jdbcUrl: String,
    val username: String,
    val password: String,
    val driverClassName: String,
    val packages: List<String>,
    val mapperLocation: String? = null
)

enum class DaoType {
    JPA, MYBATIS
}
