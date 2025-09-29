package com.solly.commonspring.aop

import com.solly.commonspring.properties.CommonLogProperties
import com.solly.commonspring.util.errorLog
import com.solly.commonspring.util.infoLog
import com.solly.commonspring.vo.BaseRequestVo
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.web.bind.annotation.*
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.stream.Stream

@Aspect
class LoggingAop (
    private val logProperties: CommonLogProperties,
) {

    @Pointcut(
            "(@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)) &&" +
            "!(within(org.springdoc..*) || within(springfox.documentation..*))"
    )
    private fun logging() {}

    // 컨트롤러를 실행하기전
    @Before("logging()")
    fun loggingBefore(joinPoint: JoinPoint) {
        if(logProperties.logging) {
            try {
                infoLog("URL : ${getRequestUrl(joinPoint, joinPoint.signature.declaringType)}")
            } catch (e: Exception) {

            }

        }

        if(logProperties.logging && logProperties.requestLog) {
            joinPoint.args.forEach {
                try {
                    if(it is BaseRequestVo) {
                        infoLog("request : $it")
                    } else {
                        infoLog("request : $it")
                    }
                } catch (e: Exception) {
                    infoLog("request : $it")
                }
            }
        }
    }

    // 컨트롤러 처리를 완료하고 response를 보낼때
    @AfterReturning("logging()", returning = "result")
    fun loggingAfter(joinPoint: JoinPoint, result: Any) {
        if(logProperties.logging && logProperties.responseLog) {
            infoLog("response : $result")
        }
    }

    @AfterThrowing("logging()", throwing = "exception")
    fun errorLogging(joinPoint: JoinPoint, exception: Exception) {
        if(logProperties.logging && logProperties.responseLog) {
            errorLog(exception)
        }
    }

    // url 가져오기
    private fun getRequestUrl(joinPoint: JoinPoint, clazz: Class<*>): String? {
        val methodSignature: MethodSignature = joinPoint.signature as MethodSignature
        val method: Method = methodSignature.method
        val requestMapping = clazz.getAnnotation(RequestMapping::class.java) as RequestMapping
        val baseUrl: String = requestMapping.value[0]
        return Stream
            .of(
                GetMapping::class.java,
                PutMapping::class.java,
                PostMapping::class.java,
                PatchMapping::class.java,
                DeleteMapping::class.java,
                RequestMapping::class.java
            ).filter { mappingClass -> method.isAnnotationPresent(mappingClass) }
            .map { mappingClass -> getUrl(method, mappingClass, baseUrl) }
            .findFirst().orElse(null)
    }

    /* httpMETHOD + requestURI 를 반환 */
    private fun getUrl(method: Method, annotationClass: Class<out Annotation>, baseUrl: String): String? {
        val annotation = method.getAnnotation(annotationClass)
        val value: Array<String>
        var httpMethod: String? = null
        try {
            value = annotationClass.getMethod("value").invoke(annotation) as Array<String>
            httpMethod = annotationClass.simpleName.replace("Mapping", "").toUpperCase()
        } catch (e: IllegalAccessException) {
            return null
        } catch (e: NoSuchMethodException) {
            return null
        } catch (e: InvocationTargetException) {
            return null
        }
        return String.format("%s %s%s", httpMethod, baseUrl, if (value.isNotEmpty()) value[0] else "")
    }
}
