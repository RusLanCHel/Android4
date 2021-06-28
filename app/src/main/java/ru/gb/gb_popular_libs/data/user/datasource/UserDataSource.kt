package ru.gb.gb_popular_libs.data.user.datasource

import io.reactivex.Observable
import io.reactivex.Single
import ru.gb.gb_popular_libs.data.user.model.GitHubUser
import ru.gb.gb_popular_libs.data.user.model.GitHubUserRepository

interface UserDataSource {

    fun fetchUsers(): Observable<List<GitHubUser>>

    fun fetchUserByLogin(login: String): Single<GitHubUser>

    fun fetchUserRepositories(login: String): Single<List<GitHubUserRepository>>
}