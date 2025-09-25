package com.solly.commonspring.annotation

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Repository

@Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Repository
annotation class PersistenceAdapter (
    @get:AliasFor(annotation = Repository::class)
    val value: String = ""
)
