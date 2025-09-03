package com.solly.commonspring.annotation

import org.springframework.context.annotation.Conditional

/**
 * 운영환경에 따른 빈 등록 여부를 결정하는 Conditaional 어노테이션
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Conditional(OnProfileExpressionCondition::class)
annotation class ConditionalOnProfileExpression(
    val value: Array<String>
)
