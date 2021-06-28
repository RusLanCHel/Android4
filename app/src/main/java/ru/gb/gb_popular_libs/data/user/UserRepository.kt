package ru.gb.gb_popular_libs.data.user

import io.reactivex.Observable
import io.reactivex.Single
import ru.gb.gb_popular_libs.data.user.model.GitHubUser
import ru.gb.gb_popular_libs.data.user.model.GitHubUserRepository

interface UserRepository {

    /**
     * Возвращает список пользователей.
     * @return список пользователей
     */
    fun fetchUsers(): Observable<List<GitHubUser>>

    /**
     * Возвращает пользователя по логину.
     * @param login логин пользователя
     * @return пользователь
     */
    fun fetchUserByLogin(login: String): Observable<GitHubUser>

    fun fetchUserRepositories(login: String): Single<List<GitHubUserRepository>>
}