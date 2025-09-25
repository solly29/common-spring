package com.solly.commonspring.exception

import com.solly.commonspring.vo.ResultCode
import java.lang.RuntimeException

open class GlobalException (
    val displayMsg: String?,
    val resultCode: ResultCode,
    val errorMsg: String? = null
) : RuntimeException(displayMsg ?: resultCode.message) {
}
