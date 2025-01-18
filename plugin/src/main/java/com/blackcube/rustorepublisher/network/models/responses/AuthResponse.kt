package com.blackcube.rustorepublisher.network.models.responses

/**
 * Auth response.
 *
 * @property code Response code indicating the result of the request.
 * **Example**: `"error"` or `"OK"`
 * @property message Decryption of the response code, in case of an error.
 * @property body see [AuthResponseBody]
 * @property timestamp Timestamp of the response in ISO 8601 format.
 * **Example**: `"2022-07-08T13:24:41.8328711+03:00"`
 */
data class AuthResponse(
    val code: String,
    val message: String?,
    val body: AuthResponseBody,
    val timestamp: String
)

/**
 * Auth response body.
 *
 * @property jwe Access token to the RuStore API. Reusable.
 * @property ttl The lifetime of the token in seconds. The lifetime is 900 seconds.
 */
data class AuthResponseBody(
    val jwe: String,
    val ttl: Int
)