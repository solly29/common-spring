package com.solly.commonspring.vo

interface PageVo : BaseResponseVo {
    val page: Int
    val pageSize: Int
}
