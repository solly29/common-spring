package com.solly.commonspring.annotation

import org.springframework.core.annotation.AliasFor
import org.springframework.web.bind.annotation.RestController

@Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@RestController
annotation class WebAdapter (
    @get:AliasFor(annotation = RestController::class)
    val value: String = ""
)
