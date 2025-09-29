package com.solly.commonspring.util

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * autoApply = true를 하게 되면
 * entity에 @Convert 설정을 해주지 않아도 해당 타입으로 받게되면 자동으로 컨버터해준다.
 */

@Converter
class LocalDateTimeToStringConverter : AttributeConverter<LocalDateTime, String> {
    override fun convertToEntityAttribute(p0: String?): LocalDateTime? {
        return if(!p0.isNullOrEmpty()) {
            p0.toLocalDateTime("yyyyMMddHHmmss")
        } else null
    }

    override fun convertToDatabaseColumn(p0: LocalDateTime?): String? {
        return p0?.stringFormat("yyyyMMddHHmmss")
    }
}

@Converter
class LocalDateTimeToStringConverter2 : AttributeConverter<LocalDateTime, String> {
    override fun convertToEntityAttribute(p0: String?): LocalDateTime? {
        return if(!p0.isNullOrEmpty()) {
            p0.toLocalDateTime("yyyy-MM-dd HH:mm:ss.SSS")
        } else null
    }

    override fun convertToDatabaseColumn(p0: LocalDateTime?): String? {
        return p0?.stringFormat("yyyy-MM-dd HH:mm:ss.SSS")
    }
}

@Converter
class LocalDateToStringConverter : AttributeConverter<LocalDate, String> {
    override fun convertToEntityAttribute(p0: String?): LocalDate? {
        return if(!p0.isNullOrEmpty()) {
            p0.toLocalDate("yyyyMMdd")
        } else null
    }

    override fun convertToDatabaseColumn(p0: LocalDate?): String? {
        return p0?.stringFormat("yyyyMMdd")
    }
}
