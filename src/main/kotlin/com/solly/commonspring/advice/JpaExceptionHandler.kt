package com.solly.commonspring.advice

import com.solly.commonspring.util.errorLog
import com.solly.commonspring.util.resultErrorMapper
import com.solly.commonspring.vo.ResultVo
import org.hibernate.LazyInitializationException
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.orm.jpa.JpaSystemException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.persistence.NoResultException
import javax.persistence.NonUniqueResultException

@ConditionalOnClass(name = ["javax.persistence.NoResultException"])
@RestControllerAdvice
class JpaExceptionHandler {

    // ✅ 단일 조회 결과가 없을 때
    @ExceptionHandler(NoResultException::class)
    fun handleNoResultException(e: NoResultException): ResultVo<Nothing> {
        errorLog("조회 결과 없음: ${e.message}")
        e.printStackTrace()
        return e.resultErrorMapper(displayMsg = "시스템 관리자에게 문의하세요.", errorMsg = "해당 데이터를 찾을 수 없습니다.")
    }

    // ✅ 단일 조회 결과가 2개 이상일 때
    @ExceptionHandler(NonUniqueResultException::class)
    fun handleNonUniqueResultException(e: NonUniqueResultException): ResultVo<Nothing> {
        errorLog("중복 결과 예외: ${e.message}")
        e.printStackTrace()
        return e.resultErrorMapper(displayMsg = "시스템 관리자에게 문의하세요.", errorMsg = "중복된 데이터가 존재합니다.")
    }

    // ✅ JPA 내부 예외 (JpaSystemException)
    @ExceptionHandler(JpaSystemException::class)
    fun handleJpaSystemException(e: JpaSystemException): ResultVo<Nothing> {
        errorLog("JPA 처리 실패: ${e.message}")
        e.printStackTrace()
        return e.resultErrorMapper(displayMsg = "시스템 관리자에게 문의하세요.", errorMsg = "JPA 데이터 처리 중 문제가 발생")
    }

    // ✅ 타입 변환 오류 (예: DTO 매핑 실패)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResultVo<Nothing> {
        errorLog("잘못된 인자: ${e.message}")
        e.printStackTrace()
        return e.resultErrorMapper(displayMsg = "시스템 관리자에게 문의하세요.", errorMsg = "요청 파라미터가 올바르지 않습니다.")
    }

    // ✅ 데이터 무결성 위반
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(e: DataIntegrityViolationException): ResultVo<Nothing> {
        errorLog("데이터 무결성 위반: ${e.message}")
        e.printStackTrace()
        return e.resultErrorMapper(displayMsg = "시스템 관리자에게 문의하세요.", errorMsg = "데이터 무결성 제약조건을 위반했습니다.")
    }

    // ✅ LazyInitializationException (트랜잭션 밖에서 Lazy 로딩)
    @ExceptionHandler(LazyInitializationException::class)
    fun handleLazyInitializationException(e: LazyInitializationException): ResultVo<Nothing> {
        errorLog("Lazy 로딩 실패: ${e.message}")
        e.printStackTrace()
        return e.resultErrorMapper(displayMsg = "시스템 관리자에게 문의하세요.", errorMsg = "지연 로딩 중 문제가 발생했습니다.")
    }

}