package ru.gb.gb_popular_libs.presentation.abs

import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState

interface LoadingView : MvpView {

    /**
     * Показывает процесс загрузки.
     */
    @SingleState
    fun showLoading()

}