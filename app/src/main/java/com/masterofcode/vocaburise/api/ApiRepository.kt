package com.masterofcode.vocaburise.api

import com.github.simonpercic.oklog3.OkLogInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.masterofcode.vocaburise.BuildConfig
import com.masterofcode.vocaburise.preferences.IUserPrefsManager
import com.masterofcode.vocaburise.preferences.UserPrefsManager
import io.reactivex.Single
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

/**
 * Created by andrews on 30.04.18.
 */

class ApiRepository : IApiRepository {

    companion object {
        private val BASE_URL = "http://vocaburise.herokuapp.com/api/v1/"
    }

    private val api by lazy {
        val okHttp = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request()
                    try {
                        UserPrefsManager.accessToken?.let {
                            chain.proceed(
                                    request.newBuilder()
                                            .addHeader("Authorization", "Bearer $it")
                                            .build()
                            )
                        } ?: chain.proceed(request)
                    } catch (e: Exception) {
                        buildEmptyBadRequestResponse(request)
                    }
                }
                .addInterceptor(OkLogInterceptor.builder()
                        .withRequestHeaders(true)
                        .withResponseHeaders(true)
                        .build())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                })
                .build()

        val gson = GsonConverterFactory.create(
                GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
        )

        Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(gson)
                .client(okHttp)
                .baseUrl(BASE_URL)
                .build()
                .create(ApiService::class.java)
    }

    private fun buildEmptyBadRequestResponse(request: Request): Response? {
        return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(HttpURLConnection.HTTP_BAD_REQUEST)
                .message("Failed to add header")
                .body(ResponseBody.create(MediaType.parse("Failed to add header"), ""))
                .build()
    }

    override fun signIn(login: String, password: String): Single<Boolean> = api.signIn(login, password)

}