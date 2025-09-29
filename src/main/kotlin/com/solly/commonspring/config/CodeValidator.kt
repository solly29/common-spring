package com.solly.commonspring.config

import com.solly.commonspring.annotation.CodeValidAnnotation
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.collections.contains

class CodeValidator : ConstraintValidator<CodeValidAnnotation, String> {

    private var codes: Array<String>? = null

    override fun initialize(constraintAnnotation: CodeValidAnnotation?) {
        codes = constraintAnnotation?.codes
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) return false

        return codes?.contains(value) ?: false
    }
}
