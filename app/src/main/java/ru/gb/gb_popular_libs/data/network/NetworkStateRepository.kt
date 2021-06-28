package ru.gb.gb_popular_libs.data.network

import io.reactivex.Observable

interface NetworkStateRepository {

    fun watchForNetworkState(): Observable<NetworkState>

}