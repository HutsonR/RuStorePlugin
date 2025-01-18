package com.blackcube.rustorepublisher.network.provider

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.File
import java.io.FileWriter
import java.io.IOException

class LoggingInterceptor(private val logFile: File) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Логирование тела запроса
        val requestBody = request.body
        val requestBodyContent = requestBody?.let {
            val buffer = okio.Buffer()
            it.writeTo(buffer)
            buffer.readUtf8()
        } ?: ""

        logFile.appendText("Request URL: ${request.url}\n")
        logFile.appendText("Request Method: ${request.method}\n")
        logFile.appendText("Request Headers: ${request.headers}\n")
        logFile.appendText("Request Body: $requestBodyContent\n")

        // Выполняем запрос
        val response = chain.proceed(request)

        // Логирование тела ответа
        val responseBody = response.body
        val content = responseBody?.string() ?: ""

        logFile.appendText("Response Code: ${response.code}\n")
        logFile.appendText("Response Body: $content\n")
        logFile.appendText("----------\n")

        // Восстанавливаем тело ответа для дальнейшей обработки
        return response.newBuilder()
            .body(ResponseBody.create(responseBody?.contentType(), content))
            .build()
    }
}