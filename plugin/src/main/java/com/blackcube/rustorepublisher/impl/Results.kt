package com.blackcube.rustorepublisher.impl

import com.blackcube.rustorepublisher.network.models.responses.AppsResponseBody
import com.blackcube.rustorepublisher.network.models.responses.AuthResponseBody

internal typealias AuthResult = Result<AuthResponseBody>
internal typealias AppsResult = Result<AppsResponseBody>
internal typealias DraftResult = Result<Int>
internal typealias UploadResult = Result<Unit>

sealed class Result<out T> {
    data class Success<out T>(val response: T) : Result<T>()

    data class Failure(private val e: Exception, private val message: String? = null) : Result<Nothing>() {
        /** Wraps the error in a new exception with [message]. */
        fun rethrow(): Nothing = throw IllegalStateException(message.orEmpty(), e)
    }
}