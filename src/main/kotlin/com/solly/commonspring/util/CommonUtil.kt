@file:JvmName("CommonUtil")
package com.solly.commonspring.util

import javax.servlet.http.HttpServletRequest
import kotlin.ranges.rangeTo
import kotlin.text.isEmpty
import kotlin.text.toDouble

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

//fun <T : CustomException> Int.checkDataChangeException(msg: String, cls: KClass<T>): Unit = let {
//    val ex = cls::class.createInstance()
//    if(it < 1) throw cls::class.createInstance()
//}

fun isWithin1kmRadius(mapX : String, mapY : String, requestX : String, requestY : String) : Boolean{
    var gisX = mapX.toDouble()
    var gisY = mapY.toDouble()

    var gisxStartValue = gisX - 0.011
    var gisxEndValue = gisX + 0.011

    var gisyStartValue = gisY - 0.0095
    var gisyEndValue = gisY + 0.0095

    var isEnableXValue = false
    var isEnableYValue = false

    if(requestX.toDouble() in gisxStartValue..gisxEndValue){
        isEnableXValue = true
    }

    if(requestY.toDouble() in gisyStartValue..gisyEndValue){
        isEnableYValue = true
    }

    return isEnableXValue && isEnableYValue
}