package com.solly.commonspring.vo

import com.solly.commonspring.util.stringFormat
import java.time.LocalDateTime

data class ResultVo<T>(
    val success: Boolean,
    val resultCode: String,
    val resultData: List<T> = listOf(),
    val metadata: ResultMetaData? = null,
    val resultMsg: String = "",
    val error: ResultErrorVo? = null,
)

data class ResultErrorVo(
    val type: String,
    val reason: String,
    val errorMsg: String = "",
)

data class ResultMetaData(
    val timestamp: String = LocalDateTime.now().stringFormat("yyyy-MM-dd HH:mm:ss") ?: "",
    val page: Int = 0,
    val pageSize: Int = 0,
    val resultSize: Int = 0,
    val totalSize: Int = 0,
    val traceId: String? = null,
)
