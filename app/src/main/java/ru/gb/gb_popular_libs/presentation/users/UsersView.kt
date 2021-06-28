package ru.gb.gb_popular_libs.presentation.users

import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState
import ru.gb.gb_popular_libs.data.user.model.GitHubUser
import ru.gb.gb_popular_libs.presentation.abs.ErrorView
import ru.gb.gb_popular_libs.presentation.abs.LoadingView

interface UsersView : LoadingView, ErrorView, MvpView {

    /**
     * Показывает список пользователей.
     * @param users список пользователей
     */
    @SingleState
    fun showUsers(users: List<GitHubUser>)

}