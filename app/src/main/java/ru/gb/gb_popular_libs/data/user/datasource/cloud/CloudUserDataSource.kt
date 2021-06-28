package ru.gb.gb_popular_libs.data.user.datasource.cloud

import io.reactivex.Observable
import io.reactivex.Single
import ru.gb.gb_popular_libs.data.api.GitHubApi
import ru.gb.gb_popular_libs.data.user.datasource.UserDataSource
import ru.gb.gb_popular_libs.data.user.model.GitHubUser
import ru.gb.gb_popular_libs.data.user.model.GitHubUserRepository
import java.util.concurrent.TimeUnit.SECONDS

class CloudUserDataSource(private val gitHubApi: GitHubApi) : UserDataSource {

    override fun fetchUsers(): Observable<List<GitHubUser>> =
        gitHubApi
            .fetchUsers()
            .delay(1L, SECONDS)
            .toObservable()

    override fun fetchUserByLogin(login: String): Single<GitHubUser> =
        gitHubApi
            .fetchUserByLogin(login)

    override fun fetchUserRepositories(login: String): Single<List<GitHubUserRepository>> =
        gitHubApi
            .fetchUserRepositories(login)


}