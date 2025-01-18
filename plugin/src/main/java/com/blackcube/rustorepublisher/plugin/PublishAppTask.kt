package com.blackcube.rustorepublisher.plugin

import com.blackcube.rustorepublisher.impl.RuStorePublisherImpl
import com.blackcube.rustorepublisher.impl.models.ServicesType
import com.blackcube.rustorepublisher.network.models.requests.PublishType
import com.blackcube.rustorepublisher.impl.Result
import com.blackcube.rustorepublisher.network.provider.RuStoreRetrofitProvider
import java.io.File

class PublishAppTask { // : DefaultTask()
    var file: File? = null
    private val projectName = "com.blackcube.starwars"
    private val publisher by lazy {
        val ruStoreApi = RuStoreRetrofitProvider().createAuthApi()
        RuStorePublisherImpl(ruStoreApi)
    }

    fun executeTask() {
        try {
            val keyId = getKeyId()
            val privateKey = getPrivateKey()

            val token = getToken(keyId, privateKey)

//            val draftId = createDraft(token)

//            uploadIcon(
//                token = token,
//                draftId = draftId
//            )

//            uploadFile(
//                isApk = true,
//                token = token,
//                draftId = draftId
//            )
        } catch (e: Exception) {
            e.printStackTrace()
            println("Failed to upload file")
        }
    }

    private fun getKeyId(): String {
        // ID private key in RuStore Console
//        System.getenv(RUSTORE_PRIVATE_KEY_ID)?.let {
//            println("Key id successfully received $it ${it.length}")
//            return it
//        }

        "2351022811".let {
            println("Key id successfully received $it ${it.length}")
            return it
        }

        error(
            """
            |Key id not initialize.
        """.trimMargin()
        )
    }

    private fun getPrivateKey(): String {
        // Generated in RuStore Console
        "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDYMVfBlgRje5C6KFQ4tlrbT6kGKzpxd4YzWx4Fw6L2QWaB49H37P86TuL49pB6izDmYe6rLvPyRGUDrn1Y1vGGBFpf26RZL57fq2K+uJqpng9rigytoy5pJQQ9aTbVSqDCOticVhkbYrTZBm+d7C+jYMWwrrODfyrBEgVSFGl4s8BpNGl4658iqfN17iajEiaNunTrapGMWReAcoxb8iJVpZ0GSxXqd/2dcmUSk2wgYo9pyP+w0i+zhs3hnxo34nygr3PWbmQYYanRSSIWQSQl74tC7WolJkAMmwplv8hp+ZahKOFHOF27D3QW9fIisd0hEVhkvh8IYWwAhZ8QMQZ3AgMBAAECggEADfyNol/budOEYF7I9oBQFKpf+euxblmeuSAKEbJl9RhaRhX6MnF6BtVhGF1CdjZSFapWGZ7buAIJWcZTCq/UbhvfyVE5UTtkKmR5BEC6Lmk9syXgixoPBkz93yE5VtQ33hXJ7ldUBkvWly6agwHrbqtaLtBZzpL04T4sUb9k6wklL8IVOtYjc0QskM9Jd6Gi+aiGEnDUa1gVnVzNiQws6tp7H/vlPkV4vPYUIHUX9ImaW9+Ib4HZoLDMIRvgE9rkZo7JrUgJZtVlZ55moMQWhCmokZK/0t4yMuHoFVbdqZN8Gj7QgT0k3UFmpkTIKUr8wrfpG1nQ0WQ1hhxTWOyZNQKBgQDnyYlKvjmZ7UEcskZ7pY8jtcMs1GwS7Hu4KRGxyypeGO/rZaq6Jp7jnagEoMutabqAE9D/uPKE+KVYyZOL8kcXSbt0q732JTKpcd746uqiCbt/psp1MtHbZuXC0IQFUwVRIHbXyxTcBoucURnspCsiJNYVmE9cSK28pOU6132TfQKBgQDuxscR8ie8Ua71/j/Xiuy3QXdCNgjk7o5hoHjJvbB+HhXx6jPnibJD7iWXeLRYMcoaB8RCwcBDBVzJMGnqoamOrxzlLnx2ZhKOAJhzTep3r4wTYoEAxImFv4zHk8BOMykJrXcdzJWdyANvXJfbqMhPhEjkl4V4mOOHORMmYnM8AwKBgCe9IdCytGVzi8pDtlnUlKnmq8Ov16eW688zG1mEH4tqcxfEonSv0mXrzbadas4cZmE3BIoTR2EM0CxcwvMnYpDXj0/9ceAVmI38e+HpRLjrOj40dlmJkPkwtwujfD79cNfr3PQ6WSUv6heuv+cWbl64OgwRxOlpfH/vNW3VLC5xAoGAZgS2D1vYUGTSDTZpTo2D4DRvYPG3USD/4oRutzt9yhTOqYK4VIgHi3CXzST8YOY9BGwnFaK0o64J28f8qQwTZuw4u07mTKwF53gnqrQypppxjeK7XXmbjHKwEqF1O7QuJ31e9HNxxW0s0orMM0Tk+Nx7THfUxhcEt8TSAsdzlsMCgYBh9kI/GVvmV1mUou6qvI2b2wLsAJaaPhxsCQgXJwYqJNSIIEigbd79/2pWcHY3kWNXslgRXxBepfFzXn6qByKa0xDPuhGt05fX5MaTIuAYc27tDhwI3FKfEBUquaR1CeoahRA2PKCEWTkLbL817WbHaEQu4++jxQAvy4eqzKSdqg=="
        .let {
            println("Private key successfully received $it ${it.length}")
            return it
        }

        error(
            """
            |Private key not initialize.
        """.trimMargin()
        )
    }

    private fun getToken(keyId: String, privateKey: String): String {
        return when (val result = publisher.getToken(keyId, privateKey)) {
            is Result.Success -> {
                println("Log jwe ${result.response.jwe}")
                result.response.jwe
            }

            is Result.Failure -> {
                result.rethrow()
            }
        }
    }

    private fun createDraft(token: String): Int {
        val result = publisher.createDraft(
            token = token,
            packageName = projectName,
            publishType = PublishType.MANUAL
        )

        return when (result) {
            is Result.Success -> {
                println("Log createDraft Success")
                result.response
            }

            is Result.Failure -> {
                println("Log createDraft Failure")
                result.rethrow()
            }
        }
    }

    private fun uploadFile(isApk: Boolean, token: String, draftId: Int) {
        val filePath = file
        val result = if (isApk) {
            println("Log uploadFile uploadApk")
            publisher.uploadApk(
                token = token,
                draftId = draftId,
                filePath = filePath!!,
                packageName = projectName,
                servicesType = ServicesType.Unknown,
                isMainApk = false
            )
        } else {
            publisher.uploadAab(
                token = token,
                draftId = draftId,
                filePath = filePath!!,
                packageName = projectName
            )
        }

        return when (result) {
            is Result.Success -> {
                println("The file with draftId - ($draftId) has been uploaded successfully!")
            }

            is Result.Failure -> {
                result.rethrow()
            }
        }
    }

    private fun uploadIcon(token: String, draftId: Int) {
        val filePath = file
        val result = publisher.uploadIcon(
            token = token,
            draftId = draftId,
            filePath = filePath!!,
            packageName = projectName
        )

        return when (result) {
            is Result.Success -> {
                println("The Icon with draftId - ($draftId) has been uploaded successfully!")
            }

            is Result.Failure -> {
                result.rethrow()
            }
        }
    }

    private fun getFile(isApk: Boolean): File {
        val filePath = File("C:\\Users\\tuzov\\AndroidStudioProjects\\StarWars\\app\\release")
        println("File path: $filePath")

        val fileExtension = if (isApk) APK_EXTENSION else AAB_EXTENSION

        filePath.walk()
            .filter { it.isFile && it.extension == fileExtension }
            .firstOrNull()
            ?.let {
                println("${fileExtension.uppercase()} file was found in the following path: ${it.absolutePath} with size ${it.length() / 1024 / 1024} МБ")
                return it
            }
        error(
            """
            |${fileExtension.uppercase()} file not found.
        """.trimMargin()
        )
    }

    companion object {
        private const val AAB_EXTENSION = "aab"
        private const val APK_EXTENSION = "apk"

        private const val RUSTORE_PRIVATE_KEY = "CI_RUSTORE_PRIVATE_KEY"
        private const val RUSTORE_PRIVATE_KEY_ID = "CI_RUSTORE_KEY_ID"
        private const val RUSTORE_FILE_PATH_TO_UPLOAD = "CI_PROJECT_DIR"
    }
}