@file:JvmName("JpaUtil")
package com.solly.commonspring.util

import java.util.Optional

fun <T> Optional<T>.toNullable(): T? = this.orElse(null)