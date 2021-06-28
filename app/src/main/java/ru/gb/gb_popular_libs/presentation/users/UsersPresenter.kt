package ru.gb.gb_popular_libs.presentation.users

import com.github.terrakok.cicerone.Router
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import moxy.MvpPresenter
import ru.gb.gb_popular_libs.data.network.NetworkStateRepository
import ru.gb.gb_popular_libs.data.user.UserRepository
import ru.gb.gb_popular_libs.data.user.model.GitHubUser
import ru.gb.gb_popular_libs.presentation.user.UserScreen
import ru.gb.gb_popular_libs.data.network.NetworkState.CONNECTED
import ru.gb.gb_popular_libs.scheduler.Schedulers

class UsersPresenter(
    private val userRepository: UserRepository,
    private val networkStateRepository: NetworkStateRepository,
    private val router: Router,
    private val schedulers: Schedulers,
): MvpPresenter<UsersView>() {

    private var disposables =  CompositeDisposable()

    override fun onFirstViewAttach() {
        disposables +=
            networkStateRepository
                .watchForNetworkState()
                .filter { networkState -> networkState == CONNECTED }
                .observeOn(schedulers.main())
                .doOnNext { displayUsers() }
                .subscribeOn(schedulers.background())
                .subscribe()

        displayUsers()
    }


    private fun displayUsers() {
        viewState.showLoading()

        disposables +=
            userRepository
                .fetchUsers()
                .map { users -> users.map(UserMapper::map) }
                .observeOn(schedulers.main())
                .subscribeOn(schedulers.background())
                .subscribe(
                    ::onFetchUsersSuccess,
                    ::onFetchUsersError
                )
    }

    private fun onFetchUsersSuccess(users: List<GitHubUser>) {
        viewState.showUsers(users)
    }

    private fun onFetchUsersError(error: Throwable) {
        viewState.showError(error.message)
    }

    fun displayUser(user: GitHubUser) =
        router.navigateTo(UserScreen(user.login))

    override fun onDestroy() {
        super.onDestroy()

        disposables.dispose()
    }

}