@file:JvmName("CommonUtil")
package com.solly.commonspring.util

import javax.servlet.http.HttpServletRequest

fun getRemoteIp(request: HttpServletRequest): String? {
    var ip: String? = request.getHeader("X-FORWARDED-FOR")

    //proxy 환경일 경우
    if (ip == null || ip.isEmpty()) {
        ip = request.getHeader("Proxy-Client-IP");
    }

    //웹로직 서버일 경우
    if (ip == null || ip.isEmpty()) {
        ip = request.getHeader("WL-Proxy-Client-IP");
    }

    if (ip == null || ip.isEmpty()) {
        ip = request.remoteAddr
    }

     return ip
}