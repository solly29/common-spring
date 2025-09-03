package com.solly.commonspring.annotation

import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata

/**
 * 운영환경에 따라서 빈을 등록 시킬건지 결정하는 condition이다.
 */
class OnProfileExpressionCondition : Condition {

    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
        val attrs = metadata.getAnnotationAttributes(ConditionalOnProfileExpression::class.java.name)
        val activeProfiles = context.environment.getProperty("spring.profiles.default")
        return (attrs?.get("value") as Array<*>).contains(activeProfiles) // 기본값 true
    }
}
