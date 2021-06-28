package ru.gb.gb_popular_libs.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.gb.gb_popular_libs.data.user.model.GitHubUser
import ru.gb.gb_popular_libs.data.user.model.GitHubUserRepository

interface GitHubApi {

    @GET("/users")
    fun fetchUsers(@Query("since") since: Int? = null): Single<List<GitHubUser>>

    @GET("/users/{username}")
    fun fetchUserByLogin(@Path("username") login: String): Single<GitHubUser>

    @GET("/users/{username}/repos")
    fun fetchUserRepositories(@Path("username") login: String): Single<List<GitHubUserRepository>>

}