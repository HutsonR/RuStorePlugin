package com.blackcube.rustorepublisher.network.provider

import com.blackcube.rustorepublisher.network.api.RuStoreApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal class RuStoreRetrofitProvider {

    private val retrofit = createRetrofit()

    fun createAuthApi(): RuStoreApi = retrofit.create(RuStoreApi::class.java)

    private fun createRetrofit(): Retrofit {
//        val logger = HttpLoggingInterceptor()
//        logger.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
//            .addInterceptor(logger)
//            .addInterceptor(LoggingInterceptor(file))
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        private const val BASE_URL = "https://public-api.rustore.ru/public/"
    }
}