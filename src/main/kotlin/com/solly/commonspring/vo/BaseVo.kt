package com.solly.commonspring.vo

interface BaseVo

interface BaseRequestVo

interface BaseResponseVo

interface BaseLanguageVo : BaseRequestVo {
    var language: String
}
