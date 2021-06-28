package ru.gb.gb_popular_libs.di.module.user

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.gb.gb_popular_libs.presentation.user.UserFragment
import ru.gb.gb_popular_libs.presentation.users.UsersFragment

@Module
abstract class UserUiModule {

    @ContributesAndroidInjector
    abstract fun bindUsersFragment(): UsersFragment

    @ContributesAndroidInjector
    abstract fun bindUserFragment(): UserFragment

}