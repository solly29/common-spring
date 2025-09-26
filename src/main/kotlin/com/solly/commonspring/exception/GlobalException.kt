package com.solly.commonspring.exception

import com.solly.commonspring.vo.CommonResultCode

open class GlobalException (
    val displayMsg: String?,
    val resultCode: CommonResultCode,
    val errorMsg: String? = null
) : RuntimeException(displayMsg ?: resultCode.message) {
}
