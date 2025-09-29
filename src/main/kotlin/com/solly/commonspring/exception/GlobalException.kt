package com.solly.commonspring.exception

import com.solly.commonspring.vo.CommonResultCodeInterface

open class GlobalException (
    val displayMsg: String?,
    val resultCode: CommonResultCodeInterface,
    val errorMsg: String? = null
) : RuntimeException(displayMsg ?: resultCode.message) {
}
