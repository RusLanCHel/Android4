package ru.gb.gb_popular_libs.presentation.users

import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

object UsersScreen: FragmentScreen {

    override fun createFragment(factory: FragmentFactory) =
        UsersFragment.newInstance()

}