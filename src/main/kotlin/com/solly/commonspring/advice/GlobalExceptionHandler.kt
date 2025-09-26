package com.solly.commonspring.advice

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.solly.commonspring.exception.CommonFileException
import com.solly.commonspring.exception.GlobalException
import com.solly.commonspring.util.errorLog
import com.solly.commonspring.util.resultErrorMapper
import com.solly.commonspring.vo.ResultVo
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.jdbc.BadSqlGrammarException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResultVo<Nothing> {
//        errorLog(ex.message)
        ex.printStackTrace()
        return ex.resultErrorMapper(displayMsg = "시스템 관리자에게 문의하세요.")
    }

    @ExceptionHandler(GlobalException::class)
    fun handleCustomException(ex: GlobalException): ResultVo<Nothing> {
        errorLog("check")
        return ex.resultErrorMapper()
    }

    @ExceptionHandler(CommonFileException::class)
    fun handleFileDownloadException(ex: CommonFileException): ResponseEntity<Resource> {
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidException(ex: MethodArgumentNotValidException): ResultVo<Nothing> {
        val msg = ex.bindingResult.fieldErrors.joinToString {
            "[${it.field}](은)는 ${it.defaultMessage} 입력된 값: [${it.rejectedValue}]"
        }
        return ex.resultErrorMapper(errorMsg = msg, displayMsg = "시스템 관리자에게 문의하세요.")
    }

    @ExceptionHandler(BadSqlGrammarException::class)
    fun handleBadSqlGrammarException(ex: BadSqlGrammarException): ResultVo<Nothing> {
//        errorLog(ex.message)
        ex.printStackTrace()
        return ex.resultErrorMapper()
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResultVo<Nothing> {
        val message = when (val cause = ex.cause) {
            is MissingKotlinParameterException -> "파라미터를 확인해주세요.(${cause.parameter.name})"
            else -> "유효하지 않은 요청입니다."
        }
        return ex.resultErrorMapper(errorMsg = message, displayMsg = "시스템 관리자에게 문의하세요.")
    }


}
