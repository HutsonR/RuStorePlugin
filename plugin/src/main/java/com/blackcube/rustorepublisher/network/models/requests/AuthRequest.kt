package com.blackcube.rustorepublisher.network.models.requests

/**
 * @property keyId Key id
 * @property timestamp The time when the request was sent. It must not differ from the current server time by more than 60 seconds.
 * @property signature The RSA signature is a SHA512 hash from the string containing the timestamp of the request.
 * */
data class AuthRequest(
    val keyId: String,
    val timestamp: String,
    val signature: String
)
