package com.solly.commonspring.vo

/**
 *
 * RestAPI 공통 응답 코드
 *
 * 코드의 끝자리수는 커스텀하여 사용 가능.
 * ResultCode를 커스텀하려면 CommonResultCode 인터페이스 구현 필요.
 *
 */

interface CommonResultCode {
    val code: String
    val message: String
}

enum class ResultCode(override val code: String, override val message: String) : CommonResultCode {
    OK("100", "성공"), // 성공

    NOT_FOUND_ERROR("E100", "일치하는 데이터가 없습니다."), // 일치하는 데이터가 없음
    OVERLAP_ERROR("E110", "중복되는 데이터가 있습니다."), // 데이터 중복
    PERMISSION_ERROR("E120", "기능을 사용할 권한이 없습니다."), // 기능 사용 권한 없음

    CREATE_ERROR("E200", "데이터 생성에 실패했습니다."),

    UPDATE_ERROR("E300", "데이터 수정에 실패했습니다."),

    DELETE_ERROR("E400", "삭제에 실패했습니다."),

    AUTHORIZATION_ERROR("E500", "인증에 실패했습니다."),

    VALIDATION_ERROR("E600", "파라미터 validation 에러입니다."),

    FILE_ERROR("E700", "파일 오류 입니다.."),
    FILE_UPLOAD_ERROR("E710", "파일 저장에 실패했습니다."),
    FILE_DOWNLOAD_ERROR("E720", "파일 다운로드에 실패했습니다."),

    BAD_SQL_ERROR("E800", "SQL 문법 오류입니다."),

    API_LINK_ERROR("E900", "HTTP API 호출에 실패했습니다."),

    SFTP_ERROR("E1000", "SFTP 오류 입니다."),

    DEFAULT_ERROR("E000", "시스템 관리자에게 문의하세요.");
}