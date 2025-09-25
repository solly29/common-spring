package com.solly.commonspring.annotation

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class InBoundAdapter (
    @get:AliasFor(annotation = Component::class)
    val value: String = ""
)
