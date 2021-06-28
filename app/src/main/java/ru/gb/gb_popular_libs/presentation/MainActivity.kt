package ru.gb.gb_popular_libs.presentation

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import ru.gb.gb_popular_libs.lession1.R
import ru.gb.gb_popular_libs.lession1.R.layout.activity_main
import ru.gb.gb_popular_libs.presentation.abs.AbsActivity
import ru.gb.gb_popular_libs.presentation.users.UsersScreen
import ru.gb.gb_popular_libs.data.network.NetworkState
import ru.gb.gb_popular_libs.data.network.NetworkStateObservable
import javax.inject.Inject

class MainActivity : AbsActivity(activity_main) {

    private val navigator = AppNavigator(activity = this, R.id.screen_container)

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState ?: router.newRootScreen(UsersScreen)
    }

    private lateinit var disposables: CompositeDisposable

    override fun onStart() {
        super.onStart()

        disposables = CompositeDisposable()
        disposables +=
            NetworkStateObservable(this)
                .skip(1)
                .subscribe(
                    ::onNetworkStateChanged,
                    ::onNetworkStateError
                )
    }

    private fun onNetworkStateChanged(networkState: NetworkState) {
        Toast.makeText(this, "NetworkStateChanged: ${networkState.name}", LENGTH_LONG).show()
    }

    private fun onNetworkStateError(error: Throwable) {
        Toast.makeText(this, "NetworkStateError: ${error.message}", LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()

        disposables.dispose()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}
