package ru.gb.gb_popular_libs

import android.util.Log
import com.github.terrakok.cicerone.Cicerone
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins
import ru.gb.gb_popular_libs.di.DaggerPopularLibrariesComponent
import ru.gb.gb_popular_libs.scheduler.DefaultSchedulers

class PopularLibraries : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<PopularLibraries> =
        DaggerPopularLibrariesComponent
            .builder()
            .withContext(applicationContext)
            .apply {
                val cicerone = Cicerone.create()

                withRouter(cicerone.router)
                withNavigatorHolder(cicerone.getNavigatorHolder())
            }
            .withSchedulers(DefaultSchedulers)
            .build()

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler { error ->
            Log.e("GLOBAL_ERRORS", error.message.toString())
        }
    }

}