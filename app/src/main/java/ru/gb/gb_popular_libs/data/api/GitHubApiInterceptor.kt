package ru.gb.gb_popular_libs.data.api

import okhttp3.*
import ru.gb.gb_popular_libs.lession1.BuildConfig.GITHUB_USER_NAME
import ru.gb.gb_popular_libs.lession1.BuildConfig.GITHUB_USER_PASSWORD

object GitHubApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request()
                .newBuilder()
                .header("Authorization", Credentials.basic(GITHUB_USER_NAME, GITHUB_USER_PASSWORD))
                .build()
        )

}