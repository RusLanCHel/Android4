package ru.gb.gb_popular_libs.di.module.network

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.gb.gb_popular_libs.data.network.NetworkStateRepository
import ru.gb.gb_popular_libs.data.network.NetworkStateRepositoryImpl

@Module
class NetworkStateModule {

    @Provides
    fun provideNetworkStateRepository(context: Context): NetworkStateRepository =
        NetworkStateRepositoryImpl(context)

}