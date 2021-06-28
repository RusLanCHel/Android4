package ru.gb.gb_popular_libs.data.user.datasource.cache

import io.reactivex.Observable
import io.reactivex.Single
import ru.gb.gb_popular_libs.data.storage.GitHubStorage
import ru.gb.gb_popular_libs.data.storage.user.GitHubUserDao
import ru.gb.gb_popular_libs.data.user.model.GitHubUser
import ru.gb.gb_popular_libs.data.user.model.GitHubUserRepository

class CacheUserDataSourceImpl(gitHubStorage: GitHubStorage) : CacheUserDataSource {

    private val gitHubUserDao: GitHubUserDao =
        gitHubStorage
            .gitHubUserDao()

    override fun fetchUsers(): Observable<List<GitHubUser>> =
        gitHubUserDao
            .fetchUsers()

    override fun fetchUserByLogin(login: String): Single<GitHubUser> =
        gitHubUserDao
            .fetchUserByLogin(login)
            .onErrorResumeNext(Single.error(RuntimeException("Нет такого пользователя")))

    override fun fetchUserRepositories(login: String): Single<List<GitHubUserRepository>> =
        gitHubUserDao
            .fetchUserRepositories(login)

    override fun retain(users: List<GitHubUser>): Single<List<GitHubUser>> =
        gitHubUserDao
            .retain(users)
            .andThen(fetchUsers())
            .firstOrError()

    override fun retain(user: GitHubUser): Single<GitHubUser> =
        gitHubUserDao
            .retain(user)
            .andThen(fetchUserByLogin(user.login))

}