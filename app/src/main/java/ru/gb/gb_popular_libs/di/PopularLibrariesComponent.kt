package ru.gb.gb_popular_libs.di

import android.content.Context
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import ru.gb.gb_popular_libs.PopularLibraries
import ru.gb.gb_popular_libs.di.module.MainModule
import ru.gb.gb_popular_libs.di.module.network.NetworkStateModule
import ru.gb.gb_popular_libs.di.module.user.UserModule
import ru.gb.gb_popular_libs.scheduler.Schedulers
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, MainModule::class, NetworkStateModule::class, UserModule::class])
interface PopularLibrariesComponent : AndroidInjector<PopularLibraries> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun withContext(context: Context): Builder

        @BindsInstance
        fun withRouter(router: Router): Builder

        @BindsInstance
        fun withNavigatorHolder(navigatorHolder: NavigatorHolder): Builder

        @BindsInstance
        fun withSchedulers(schedulers: Schedulers): Builder

        fun build(): PopularLibrariesComponent

    }

}