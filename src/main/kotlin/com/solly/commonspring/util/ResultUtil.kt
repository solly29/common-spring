/**
 * 공통 응답(ResultVo) 생성 유틸리티
 *
 * 성공/실패 응답을 표준화된 형식으로 생성
 *
 * @author solly29
 * @see ResultVo
 * @see ResultCode
 *
 */
@file:JvmName("ResultUtil")
package com.solly.commonspring.util

import com.solly.commonspring.exception.GlobalException
import com.solly.commonspring.vo.BaseResponseVo
import com.solly.commonspring.vo.ResultCode
import com.solly.commonspring.vo.ResultErrorVo
import com.solly.commonspring.vo.ResultMetaData
import com.solly.commonspring.vo.ResultVo
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException

/**
 * 단일 객체를 공통 응답(ResultVo)로 변환하는 함수
 *
 * @param code 공통 응답의 resultCode(기본값 ResultCode.OK)
 * @param msg 공통 응답의 resultMsg(기본값 성공)
 * @param resultSize 응답 객체(resultDate)의 사이즈
 * @param totalSize 응답 데이터의 총 사이즈(페이징이 있는 경우 전체 데이터 사이즈)
 * @param page 페이징을 사용할 경우 현재 페이지
 * @param pageSize 페이징을 사용할 경우 현재 보여주는 데이터 수
 * @receiver ResultVo로 변환할 대상 객체(BaseResponseVo를 구현하고 있어야함)
 *
 */
fun <T : BaseResponseVo> T.successMapper(code: ResultCode = ResultCode.OK, msg: String = "성공", resultSize: Int = 1, totalSize: Int = 1, page: Int = 0, pageSize: Int = 0): ResultVo<T> = ResultVo(
    success = true,
    resultCode = code.code,
    resultData = listOf(this),
    metadata = ResultMetaData(
        page = page,
        pageSize = pageSize,
        resultSize = resultSize,
        totalSize = totalSize
    ),
    resultMsg = msg
)

/**
 * List 객체를 공통 응답(ResultVo)로 변환하는 함수
 *
 * @param code 공통 응답의 resultCode(기본값 ResultCode.OK)
 * @param msg 공통 응답의 resultMsg(기본값 성공)
 * @param resultSize 응답 객체(resultDate)의 사이즈
 * @param totalSize 응답 데이터의 총 사이즈(페이징이 있는 경우 전체 데이터 사이즈)
 * @param page 페이징을 사용할 경우 현재 페이지
 * @param pageSize 페이징을 사용할 경우 현재 보여주는 데이터 수
 * @receiver ResultVo로 변환할 대상 객체(BaseResponseVo를 구현하고 있어야함)
 *
 */
fun <T : BaseResponseVo> List<T>.successMapper(code: ResultCode = ResultCode.OK, msg: String = "성공", resultSize: Int = size, totalSize: Int = size, page: Int = 0, pageSize: Int = 0): ResultVo<T> = ResultVo(
    success = true,
    resultCode = code.code,
    resultData = this,
    metadata = ResultMetaData(
        page = page,
        pageSize = pageSize,
        resultSize = resultSize,
        totalSize = totalSize
    ),
    resultMsg = msg
)

/**
 * resultData가 비어있는 공통 응답(ResultVo)로 변환하는 함수
 *
 * @param code 공통 응답의 resultCode(기본값 ResultCode.OK)
 * @param msg 공통 응답의 resultMsg(기본값 성공)
 * @receiver ResultVo로 변환할 대상 객체(BaseResponseVo를 구현하고 있어야함)
 *
 */
fun <T> successBlankMapper(code: ResultCode = ResultCode.OK, msg: String = "성공"): ResultVo<T> = ResultVo(
    success = true,
    resultCode = code.code,
    resultMsg = msg,
    metadata = ResultMetaData()
)

/**
 * 에러 공통 응답(ResultVo)로 변환하는 함수
 *
 * @param code 공통 응답의 resultCode(기본값 ResultCode.DEFAULT_ERROR)
 * @param displayMsg 에러가 발생했을때 화면에 보여줘도 되는 메시지
 * @param errorMsg 에러가 발생했을때 개발자가 확인 후 조치해야하는 메시지
 *
 */
fun <T> errorMapper(code: ResultCode = ResultCode.DEFAULT_ERROR, displayMsg: String = ResultCode.DEFAULT_ERROR.message, errorMsg: String? = null): ResultVo<T> = ResultVo(
    success = false,
    resultCode = code.code,
    resultMsg = displayMsg,
    metadata = ResultMetaData(),
    error = ResultErrorVo(
        type = code.name,
        reason = errorMsg ?: code.message,
        errorMsg = displayMsg
    )
)

/**
 * RuntimeException을 공통 응답(ResultVo)로 변환하는 함수
 *
 * @param code 공통 응답의 resultCode(기본값 ResultCode.DEFAULT_ERROR)
 * @param displayMsg 에러가 발생했을때 화면에 보여줘도 되는 메시지
 * @param errorMsg 에러가 발생했을때 개발자가 확인 후 조치해야하는 메시지
 * @receiver RuntimeException을 상속 받고 있는 객체
 *
 */
fun <T : RuntimeException> T.resultErrorMapper(code: ResultCode = ResultCode.DEFAULT_ERROR, displayMsg: String? = null, errorMsg: String? = null): ResultVo<Nothing> = ResultVo(
    success = false,
    resultCode = code.code,
    resultMsg = displayMsg ?: code.message,
    metadata = ResultMetaData(),
    error = ResultErrorVo(
        type = code.name,
        reason = errorMsg ?: code.message,
        errorMsg = displayMsg ?: code.message
    )
)

/**
 * GlobalException을 공통 응답(ResultVo)로 변환하는 함수
 *
 * @receiver GlobalException을 상속 받고 있는 객체
 *
 */
fun <T : GlobalException> T.resultErrorMapper(): ResultVo<Nothing> = ResultVo(
    success = false,
    resultCode = resultCode.code,
    resultMsg = displayMsg ?: ResultCode.DEFAULT_ERROR.message,
    metadata = ResultMetaData(),
    error = ResultErrorVo(
        type = resultCode.name,
        reason = errorMsg ?: displayMsg ?: "",
        errorMsg = displayMsg ?: ResultCode.DEFAULT_ERROR.message
    )
)

/**
 * MethodArgumentNotValidException을 공통 응답(ResultVo)로 변환하는 함수
 *
 * @param code 공통 응답의 resultCode(기본값 ResultCode.DEFAULT_ERROR)
 * @param displayMsg 에러가 발생했을때 화면에 보여줘도 되는 메시지
 * @param errorMsg 에러가 발생했을때 개발자가 확인 후 조치해야하는 메시지
 * @receiver MethodArgumentNotValidException을 상속 받고 있는 객체
 *
 */
fun <T : MethodArgumentNotValidException> T.resultErrorMapper(code: ResultCode = ResultCode.VALIDATION_ERROR, errorMsg: String? = null, displayMsg: String? = null): ResultVo<Nothing> = ResultVo(
    success = false,
    resultCode = code.code,
    resultMsg = displayMsg ?: code.message,
    metadata = ResultMetaData(),
    error = ResultErrorVo(
        type = code.name,
        reason = errorMsg ?: "",
        errorMsg = displayMsg ?: code.message
    )
)

/**
 * HttpMessageNotReadableException을 공통 응답(ResultVo)로 변환하는 함수
 *
 * @param code 공통 응답의 resultCode(기본값 ResultCode.DEFAULT_ERROR)
 * @param displayMsg 에러가 발생했을때 화면에 보여줘도 되는 메시지
 * @param errorMsg 에러가 발생했을때 개발자가 확인 후 조치해야하는 메시지
 * @receiver HttpMessageNotReadableException을 상속 받고 있는 객체
 *
 */
fun <T : HttpMessageNotReadableException> T.resultErrorMapper(code: ResultCode = ResultCode.VALIDATION_ERROR, errorMsg: String? = null, displayMsg: String? = null): ResultVo<Nothing> = ResultVo(
    success = false,
    resultCode = code.code,
    resultMsg = displayMsg ?: code.message,
    metadata = ResultMetaData(),
    error = ResultErrorVo(
        type = code.name,
        reason = errorMsg ?: "",
        errorMsg = displayMsg ?: code.message
    )
)
