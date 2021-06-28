package ru.gb.gb_popular_libs.data.user

import io.reactivex.Observable
import io.reactivex.Single
import ru.gb.gb_popular_libs.data.user.datasource.UserDataSource
import ru.gb.gb_popular_libs.data.user.datasource.cache.CacheUserDataSource
import ru.gb.gb_popular_libs.data.user.model.GitHubUser
import ru.gb.gb_popular_libs.data.user.model.GitHubUserRepository
import java.util.concurrent.TimeUnit.SECONDS

class UserRepositoryImpl(
    private val cloudUserDataSource: UserDataSource,
    private val cacheUserDataSource: CacheUserDataSource,
) : UserRepository {

    override fun fetchUsers(): Observable<List<GitHubUser>> =
        cacheUserDataSource
            .fetchUsers()
            .flatMap(::fetchFromCloudIfRequired)

    private fun fetchFromCloudIfRequired(users: List<GitHubUser>): Observable<List<GitHubUser>> =
        if (users.isEmpty()) {
            cloudUserDataSource
                .fetchUsers()
                .flatMapSingle(cacheUserDataSource::retain)
        } else {
            Observable.just(users)
        }

    override fun fetchUserByLogin(login: String): Observable<GitHubUser> =
        Observable.concat(
            cacheUserDataSource
                .fetchUserByLogin(login)
                .toObservable(),
            cloudUserDataSource
                .fetchUserByLogin(login)
                .flatMap(cacheUserDataSource::retain)
                .retryWhen { error -> error.delay(5L, SECONDS) }
                .toObservable()
        )

    override fun fetchUserRepositories(login: String): Single<List<GitHubUserRepository>> {
        TODO("Not yet implemented")
    }

}