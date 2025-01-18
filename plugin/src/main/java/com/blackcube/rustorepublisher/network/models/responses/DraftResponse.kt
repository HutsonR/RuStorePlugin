package com.blackcube.rustorepublisher.network.models.responses

/**
 * Create draft response.
 *
 * @property code Response code indicating the result of the request.
 * **Example**: `"error"` or `"OK"`
 * @property message Decryption of the response code, in case of an error.
 * @property body draft ID
 * @property timestamp Timestamp of the response in ISO 8601 format.
 * **Example**: `"2022-07-08T13:24:41.8328711+03:00"`
 */
data class DraftResponse(
    val code: String,
    val message: String?,
    val timestamp: String,
    val body: Int
)