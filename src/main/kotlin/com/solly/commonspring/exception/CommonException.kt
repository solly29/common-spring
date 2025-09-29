package com.solly.commonspring.exception

import com.solly.commonspring.vo.CommonResultCode

/* 데이터 없음 */
class CommonNotFoundException (
    displayMsg: String = CommonResultCode.NOT_FOUND_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, CommonResultCode.NOT_FOUND_ERROR, errorMsg)

/* 기능 사용 권한 오류 */
class CommonPermissionException (
    displayMsg: String = CommonResultCode.PERMISSION_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, CommonResultCode.PERMISSION_ERROR, errorMsg)

/* 중복 데이터 오류 */
class CommonOverlapException (
    displayMsg: String? = CommonResultCode.OVERLAP_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, CommonResultCode.OVERLAP_ERROR, errorMsg)

/* 인증 오류 */
class CommonAuthorizationException (
    displayMsg: String? = CommonResultCode.AUTHORIZATION_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, CommonResultCode.AUTHORIZATION_ERROR, errorMsg)

/* 입력값 오류 */
class CommonValidationException (
    displayMsg: String? = CommonResultCode.VALIDATION_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, CommonResultCode.VALIDATION_ERROR, errorMsg)

/* 외부 API 호출 오류 */
class CommonApiLinkException (
    displayMsg: String? = CommonResultCode.API_LINK_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, CommonResultCode.API_LINK_ERROR, errorMsg)

/* SFTP 오류 */
class CommonSftpException (
    displayMsg: String? = CommonResultCode.SFTP_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, CommonResultCode.SFTP_ERROR, errorMsg)

/* 파일 오류 */
class CommonFileException (
    displayMsg: String? = CommonResultCode.FILE_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, CommonResultCode.FILE_ERROR, errorMsg)

/* 파일 업로드 오류 */
class CommonFileUpLoadException (
    displayMsg: String? = CommonResultCode.FILE_UPLOAD_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, CommonResultCode.FILE_UPLOAD_ERROR, errorMsg)

/* 파일 다운로드 오류 */
class CommonFileDownLoadException (
    displayMsg: String? = CommonResultCode.FILE_DOWNLOAD_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, CommonResultCode.FILE_DOWNLOAD_ERROR, errorMsg)

/* 데이터 삭제 오류 */
class CommonDeleteException (
    displayMsg: String? = CommonResultCode.DELETE_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, CommonResultCode.DELETE_ERROR, errorMsg)

/* 데이터 생성 오류 */
class CommonCreateException (
    displayMsg: String? = CommonResultCode.CREATE_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, CommonResultCode.CREATE_ERROR, errorMsg)

/* 데이터 수정 오류 */
class CommonUpdateException (
    displayMsg: String? = CommonResultCode.UPDATE_ERROR.message,
    errorMsg: String? = null
) : GlobalException(displayMsg, CommonResultCode.UPDATE_ERROR, errorMsg)