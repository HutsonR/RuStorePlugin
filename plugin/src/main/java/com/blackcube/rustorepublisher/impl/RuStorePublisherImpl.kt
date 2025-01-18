package com.blackcube.rustorepublisher.impl

import com.blackcube.rustorepublisher.impl.models.ServicesType
import com.blackcube.rustorepublisher.network.api.RuStoreApi
import com.blackcube.rustorepublisher.network.models.requests.AuthRequest
import com.blackcube.rustorepublisher.network.models.requests.DraftRequest
import com.blackcube.rustorepublisher.network.models.requests.PublishType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Base64
import java.util.Locale

internal class RuStorePublisherImpl(
    private val ruStoreApi: RuStoreApi
): RuStorePublisher {

    override fun getToken(keyId: String, privateKey: String): AuthResult {
        return try {
            val timestamp = generateTimestamp()
            val signature = generateSignature(timestamp, keyId, privateKey)
            val response = ruStoreApi.auth(
                AuthRequest(
                    keyId,
                    timestamp,
                    signature
                )
            ).execute()

            if (response.isSuccessful) {
                val authResponse = response.body()

                if (authResponse != null && authResponse.code == RESPONSE_SUCCESS_CODE) {
                    Result.Success(authResponse.body)
                } else {
                    Result.Failure(
                        e = IllegalStateException("API error"),
                        message = authResponse?.message ?: "Unknown error"
                    )
                }
            } else {
                Result.Failure(
                    e = HttpException(response),
                    message = response.errorBody()?.string()
                )
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override fun getApps(
        token: String,
        pagination: Boolean,
        continuationToken: String?,
        pageSize: String?,
        appName: String?,
        appPackage: String?,
        orderFields: String?,
        sortDirections: String?
    ): AppsResult {
        return try {
            val response = ruStoreApi.getApps(
                token,
                pagination,
                continuationToken,
                pageSize,
                appName,
                appPackage,
                orderFields,
                sortDirections
            ).execute()

            if (response.isSuccessful) {
                val getAppsResponse = response.body()

                if (getAppsResponse != null && getAppsResponse.code == RESPONSE_SUCCESS_CODE) {
                    Result.Success(getAppsResponse.body)
                } else {
                    Result.Failure(
                        e = IllegalStateException("API error"),
                        message = getAppsResponse?.message ?: "Unknown error"
                    )
                }
            } else {
                Result.Failure(
                    e = HttpException(response),
                    message = response.errorBody()?.string()
                )
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override fun createDraft(token: String, packageName: String, publishType: PublishType): DraftResult {
        return try {
            val response = ruStoreApi.createDraft(
                token,
                packageName,
                DraftRequest(publishType.name)
            ).execute()

            if (response.isSuccessful) {
                val draftResponse = response.body()

                if (draftResponse != null && draftResponse.code == RESPONSE_SUCCESS_CODE)  {
                    // Если черновика не было, и мы создали новый, то в body будет draftId
                    Result.Success(draftResponse.body)
                } else {
                    Result.Failure(
                        e = IllegalStateException("API error"),
                        message = draftResponse?.message ?: "Unknown error"
                    )
                }
            } else {
                val message = response.errorBody()?.string()
                try {
                    // Если черновик уже существовал, в message будет ошибка вида
                    // "You already have draft version with ID = XXXXXXXXXX", оттуда достаем ID существующего черновика.
                    val draftId = getIdFromMessage(message ?: "")
                    Result.Success(draftId)
                } catch (e: Exception) {
                    Result.Failure(
                        e = HttpException(response),
                        message = message
                    )
                }
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override fun uploadAab(
        token: String,
        draftId: Int,
        filePath: File,
        packageName: String
    ): UploadResult {
        return try {
            val requestFile = filePath.asRequestBody(MIME_TYPE_STREAM.toMediaTypeOrNull())
            val multipart = MultipartBody.Part.createFormData("file", filePath.name, requestFile)
            val response = ruStoreApi.uploadAab(
                token,
                packageName,
                draftId,
                multipart
            ).execute()

            if (response.isSuccessful) {
                val uploadResponse = response.body()

                if (uploadResponse != null && uploadResponse.code == RESPONSE_SUCCESS_CODE) {
                    Result.Success(Unit)
                } else {
                    Result.Failure(
                        e = IllegalStateException("API error"),
                        message = uploadResponse?.message ?: "Unknown error"
                    )
                }
            } else {
                Result.Failure(
                    e = HttpException(response),
                    message = response.errorBody()?.string()
                )
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override fun uploadApk(
        token: String,
        draftId: Int,
        filePath: File,
        packageName: String,
        servicesType: ServicesType,
        isMainApk: Boolean
    ): UploadResult {
        return try {
            val requestFile = filePath.asRequestBody(MIME_TYPE_APK.toMediaTypeOrNull())
            val multipart = MultipartBody.Part.createFormData("file", filePath.name, requestFile)
            val response = ruStoreApi.uploadApk(
                token,
                packageName,
                draftId,
                servicesType.name,
                isMainApk,
                multipart
            ).execute()

            println("Log uploadApk request $response")

            if (response.isSuccessful) {
                println("Log uploadApk response.isSuccessful")
                val uploadResponse = response.body()
                if (uploadResponse != null && uploadResponse.code == RESPONSE_SUCCESS_CODE) {
                    Result.Success(Unit)
                } else {
                    Result.Failure(
                        e = IllegalStateException("API error"),
                        message = uploadResponse?.message ?: "Unknown error"
                    )
                }
            } else {
                println("Log uploadApk response NOT isSuccessful")
                Result.Failure(
                    e = HttpException(response),
                    message = response.errorBody()?.string()
                )
            }
        } catch (e: Exception) {
            println("Log uploadApk main catch")
            Result.Failure(e)
        }
    }

    override fun uploadIcon(
        token: String,
        draftId: Int,
        filePath: File,
        packageName: String
    ): UploadResult {
        return try {
            val mediaType = when (filePath.extension.lowercase(Locale.ROOT)) {
                "jpg", "jpeg" -> "image/jpeg".toMediaTypeOrNull()
                "png" -> "image/png".toMediaTypeOrNull()
                else -> throw IllegalArgumentException("Unsupported file type")
            }
            val requestFile = filePath.asRequestBody(mediaType)
            val multipart = MultipartBody.Part.createFormData("file", filePath.name, requestFile)
            val response = ruStoreApi.uploadIcon(
                token,
                packageName,
                draftId,
                multipart
            ).execute()

            if (response.isSuccessful) {
                val uploadResponse = response.body()
                if (uploadResponse != null && uploadResponse.code == RESPONSE_SUCCESS_CODE) {
                    Result.Success(Unit)
                } else {
                    Result.Failure(
                        e = IllegalStateException("API error"),
                        message = uploadResponse?.message ?: "Unknown error"
                    )
                }
            } else {
                Result.Failure(
                    e = HttpException(response),
                    message = response.errorBody()?.string()
                )
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    private fun getIdFromMessage(message: String): Int {
        val regex = Regex("ID\\s*=\\s*(\\d+)")
        return message.let {
            if (it.contains("ID")) {
                regex.find(it)?.groupValues?.get(1)?.toInt()
                    ?: throw IllegalArgumentException("Invalid format of the message")
            } else {
                throw IllegalArgumentException("The ID is not contained in the message")
            }
        }
    }

    private fun generateTimestamp(): String =
        DateTimeFormatter.ISO_INSTANT.format(Instant.now())

    // For get token
    private fun generateSignature(timestamp: String, keyId: String, privateKey: String): String {
        val keyBytes = Base64.getDecoder().decode(privateKey)
        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        val privateKeyObj = KeyFactory.getInstance("RSA").generatePrivate(keySpec)

        val signature = Signature.getInstance("SHA512withRSA").apply {
            initSign(privateKeyObj)
            println(keyId + timestamp)
            update((keyId + timestamp).toByteArray(Charsets.UTF_8))
        }

        val signedBytes = signature.sign()
        return Base64.getEncoder().encodeToString(signedBytes)
    }

    companion object {
        private const val RESPONSE_SUCCESS_CODE = "OK"

        private const val MIME_TYPE_STREAM = "application/octet-stream"
        private const val MIME_TYPE_APK = "application/vnd.android.package-archive"
    }
}