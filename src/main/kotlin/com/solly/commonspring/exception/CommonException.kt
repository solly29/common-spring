package com.solly.commonspring.exception

import com.solly.commonspring.vo.ResultCode

/* 데이터 없음 */
class CommonNotFoundException (
    displayMsg: String = ResultCode.NOT_FOUND_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, ResultCode.NOT_FOUND_ERROR, errorMsg)

/* 기능 사용 권한 오류 */
class CommonPermissionException (
    displayMsg: String = ResultCode.PERMISSION_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, ResultCode.PERMISSION_ERROR, errorMsg)

/* 중복 데이터 오류 */
class CommonOverlapException (
    displayMsg: String? = ResultCode.OVERLAP_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, ResultCode.OVERLAP_ERROR, errorMsg)

/* 인증 오류 */
class CommonAuthorizationException (
    displayMsg: String? = ResultCode.AUTHORIZATION_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, ResultCode.AUTHORIZATION_ERROR, errorMsg)

/* 입력값 오류 */
class CommonValidationException (
    displayMsg: String? = ResultCode.VALIDATION_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, ResultCode.VALIDATION_ERROR, errorMsg)

/* 외부 API 호출 오류 */
class CommonApiLinkException (
    displayMsg: String? = ResultCode.API_LINK_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, ResultCode.API_LINK_ERROR, errorMsg)

/* SFTP 오류 */
class CommonSftpException (
    displayMsg: String? = ResultCode.SFTP_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, ResultCode.SFTP_ERROR, errorMsg)

/* 파일 오류 */
class CommonFileException (
    displayMsg: String? = ResultCode.FILE_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, ResultCode.FILE_ERROR, errorMsg)

/* 파일 업로드 오류 */
class CommonFileUpLoadException (
    displayMsg: String? = ResultCode.FILE_UPLOAD_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, ResultCode.FILE_UPLOAD_ERROR, errorMsg)

/* 파일 다운로드 오류 */
class CommonFileDownLoadException (
    displayMsg: String? = ResultCode.FILE_DOWNLOAD_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, ResultCode.FILE_DOWNLOAD_ERROR, errorMsg)

/* 데이터 삭제 오류 */
class CommonDeleteException (
    displayMsg: String? = ResultCode.DELETE_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, ResultCode.DELETE_ERROR, errorMsg)

/* 데이터 생성 오류 */
class CommonCreateException (
    displayMsg: String? = ResultCode.CREATE_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, ResultCode.CREATE_ERROR, errorMsg)

/* 데이터 수정 오류 */
class CommonUpdateException (
    displayMsg: String? = ResultCode.UPDATE_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, ResultCode.UPDATE_ERROR, errorMsg)