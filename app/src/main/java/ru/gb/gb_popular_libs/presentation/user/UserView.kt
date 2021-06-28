package ru.gb.gb_popular_libs.presentation.user

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.SingleState
import ru.gb.gb_popular_libs.data.user.model.GitHubUser
import ru.gb.gb_popular_libs.data.user.model.GitHubUserRepository
import ru.gb.gb_popular_libs.presentation.abs.ErrorView

interface UserView : ErrorView, MvpView {

    /**
     * Показывает пользователя.
     * @param user пользователь
     */
    @SingleState
    fun showUser(user: GitHubUser)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showUserRepo(repo: List<GitHubUserRepository>)

}