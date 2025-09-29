package com.solly.commonspring.annotation

import com.solly.commonspring.config.CodeValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

// TODO Enum Class를 받아서 validation 체크 하도록

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [CodeValidator::class])
annotation class CodeValidAnnotation(
    val message: String = "유효하지 않은 값을 입력했습니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val codes: Array<String>
)
