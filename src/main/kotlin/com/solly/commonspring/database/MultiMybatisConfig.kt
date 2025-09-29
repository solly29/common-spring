package com.solly.commonspring.database

import com.solly.commonspring.properties.DaoType
import com.solly.commonspring.properties.DbConfig
import com.solly.commonspring.properties.MultiDbProperties
import com.solly.commonspring.util.DataBaseUtil
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.type.JdbcType
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS
import org.mybatis.spring.mapper.MapperScannerConfigurer
import org.springframework.beans.factory.BeanCreationException
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.beans.factory.support.GenericBeanDefinition
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import java.util.function.Supplier
import javax.sql.DataSource

@Configuration
@ConditionalOnClass(SqlSessionFactoryBean::class)
class MultiMybatisConfig : BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private lateinit var environment: Environment

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        environment = applicationContext.getBean(Environment::class.java)
    }

    override fun postProcessBeanDefinitionRegistry(p0: BeanDefinitionRegistry) {

        val properties = Binder.get(environment)
            .bind("multi-datasource", MultiDbProperties::class.java)
            .orElseThrow { kotlin.IllegalStateException("Failed to bind MultiDbProperties") }

        properties.config.forEach { name, config ->
            // DataSource
            val dataSource = DataSourceBuilder.create()
                .driverClassName(config.driverClassName)
                .url(config.jdbcUrl)
                .username(config.username)
                .password(config.password)
                .build()

            p0.registerBeanDefinition(
                "${name}DataSource",
                GenericBeanDefinition().apply {
                    setBeanClass(DataSource::class.java)
                    instanceSupplier = Supplier { dataSource }
                }
            )

            if (config.type == DaoType.MYBATIS) {
                configureMybatis(name, config, dataSource, p0)
            }
        }
    }

    override fun postProcessBeanFactory(p0: ConfigurableListableBeanFactory) {

    }

    private fun configureMybatis(name: String, config: DbConfig, dataSource: DataSource, registry: BeanDefinitionRegistry) {
        val sqlSessionFactoryBean = SqlSessionFactoryBean().apply {
            setDataSource(dataSource)
            vfs = SpringBootVFS::class.java
            setMapperLocations(
                *DataBaseUtil.getInstance().resolveMapperLocations(config.mapperLocation!!)
            )
        }.`object`?.also {
            it.configuration.jdbcTypeForNull = JdbcType.VARCHAR
            it.configuration.isMapUnderscoreToCamelCase = true
        } ?: throw BeanCreationException("Mybatis cannot be configured")

        registry.registerBeanDefinition(
            "${name}SqlSessionFactory",
            GenericBeanDefinition().apply {
                setBeanClass(SqlSessionFactory::class.java)
                instanceSupplier = Supplier { sqlSessionFactoryBean }
            }
        )

        val mapperScanner = MapperScannerConfigurer().apply {
            setBasePackage(config.packages.joinToString(","))
            setSqlSessionFactoryBeanName("${name}SqlSessionFactory")
        }

        registry.registerBeanDefinition(
            "${name}MapperScannerConfigurer",
            GenericBeanDefinition().apply {
                setBeanClass(MapperScannerConfigurer::class.java)
                instanceSupplier = Supplier { mapperScanner }
            }
        )

        val txManager = DataSourceTransactionManager(dataSource)

        registry.registerBeanDefinition(
            "${name}TransactionManager",
            GenericBeanDefinition().apply {
                setBeanClass(PlatformTransactionManager::class.java)
                instanceSupplier = Supplier { txManager }
                setAttribute("org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean", true)
            }
        )
    }

}
