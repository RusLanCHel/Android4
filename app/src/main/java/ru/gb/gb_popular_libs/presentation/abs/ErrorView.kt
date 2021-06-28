package ru.gb.gb_popular_libs.presentation.abs

import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState

interface ErrorView : MvpView {

    /**
     * Показывает ошибку.
     * @param message сообщение об ошибке
     */
    @SingleState
    fun showError(message: String?)

}