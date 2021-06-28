package ru.gb.gb_popular_libs.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.gb.gb_popular_libs.presentation.MainActivity

@Module
abstract class MainModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

}