package com.solly.commonspring.util

import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.io.IOException
import kotlin.also
import kotlin.collections.asList
import kotlin.collections.forEach
import kotlin.collections.toList
import kotlin.collections.toTypedArray

/**
 * 데이터베이스 관련된 유틸 함수를 모은 클래스(싱글톤)
 */
class DataBaseUtil {

    companion object {
        private var instance: DataBaseUtil? = null

        fun getInstance(): DataBaseUtil = instance ?: DataBaseUtil().also {
            instance = it
        }
    }

    /**
     * 여러개의 Mapper 경로를 지정하기 위한 함수
     */
    fun resolveMapperLocations(vararg path: String): Array<Resource> {
        val resourceResolver = PathMatchingResourcePatternResolver()
        val mapperLocation = path.asList()
        val resources = arrayListOf<Resource>()
        mapperLocation.forEach {
            try {
                resources.addAll(resourceResolver.getResources(it).toList())
            } catch (e: IOException) {
                println(e.message)
            }
        }
        return resources.toTypedArray()
    }
}
