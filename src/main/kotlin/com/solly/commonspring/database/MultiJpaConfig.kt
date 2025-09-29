package com.solly.commonspring.database

import com.solly.commonspring.properties.DaoType
import com.solly.commonspring.properties.MultiDbProperties
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.beans.factory.support.RootBeanDefinition
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.context.annotation.Configuration
import org.springframework.core.type.filter.AnnotationTypeFilter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.stereotype.Repository

@Configuration
@ConditionalOnMissingBean
@ConditionalOnClass(name = ["javax.persistence.EntityManagerFactory"])
@EnableConfigurationProperties(MultiDbProperties::class)
class MultiJpaConfig (
    private val properties: MultiDbProperties,
    private val builder: EntityManagerFactoryBuilder
) : BeanDefinitionRegistryPostProcessor {

    @Bean
    fun entityManagerFactoryBuilder(): EntityManagerFactoryBuilder {
        return EntityManagerFactoryBuilder(
            HibernateJpaVendorAdapter(),
            emptyMap<String, Any>(),
            null
        )
    }

    override fun postProcessBeanDefinitionRegistry(p0: BeanDefinitionRegistry) {

        properties.config.forEach { key, config ->

            if(config.type == DaoType.MYBATIS) {
                return@forEach
            }

            // DataSource 생성
            val ds = DataSourceBuilder.create()
                .url(config.jdbcUrl)
                .username(config.username)
                .password(config.password)
                .driverClassName(config.driverClassName)
                .build()

            // EntityManagerFactory 생성
            val emf = builder
                .dataSource(ds)
                .packages(*config.packages.toTypedArray())
                .persistenceUnit(key)
                .build()

            p0.registerBeanDefinition(
                "${key}EntityManagerFactory",
                BeanDefinitionBuilder.genericBeanDefinition(LocalContainerEntityManagerFactoryBean::class.java) {
                    emf
                }.beanDefinition
            )

            // TransactionManager 등록
            p0.registerBeanDefinition(
                "${key}TransactionManager",
                BeanDefinitionBuilder.genericBeanDefinition(JpaTransactionManager::class.java) {
                    JpaTransactionManager(emf.`object`!!)
                }.beanDefinition
            )

            // Repository 수동 등록
            config.packages.forEach { basePkg ->
                val scanner = ClassPathScanningCandidateComponentProvider(false)
                scanner.addIncludeFilter(AnnotationTypeFilter(Repository::class.java)) // @Repository 붙은 인터페이스만

                val candidates = scanner.findCandidateComponents(basePkg)

                val em = emf.`object`!!.createEntityManager()
                val factory = JpaRepositoryFactory(em)

                candidates.forEach { beanDef ->
                    val repoClass = Class.forName(beanDef.beanClassName) as Class<JpaRepository<*, *>>
                    val repoImpl = factory.getRepository(repoClass)
                    p0.registerBeanDefinition(
                        repoClass.simpleName,
                        RootBeanDefinition(repoImpl::class.java)
                    )
                }
            }
        }
    }

    override fun postProcessBeanFactory(p0: ConfigurableListableBeanFactory) {

    }
}