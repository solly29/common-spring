package com.solly.commonspring.vo

data class ResultVo<T>(
    var resultCode: String,
    var resultMsg: String,
    val resultSize: Int = 0,
    val totalSize: Int = 0,
    val resultData: List<T> = listOf()
)
