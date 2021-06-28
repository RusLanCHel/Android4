package ru.gb.gb_popular_libs.presentation.user

import com.github.terrakok.cicerone.Router
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import moxy.MvpPresenter
import ru.gb.gb_popular_libs.scheduler.Schedulers
import ru.gb.gb_popular_libs.data.user.UserRepository
import ru.gb.gb_popular_libs.data.user.model.GitHubUser
import ru.gb.gb_popular_libs.data.user.model.GitHubUserRepository

class UserPresenter(
    private val userId: String,
    private val userRepository: UserRepository,
    private val schedulers: Schedulers,
    private val router: Router
): MvpPresenter<UserView>() {

    private var disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        disposables +=
            userRepository
                .fetchUserByLogin(userId)
                .observeOn(schedulers.main())
                .subscribeOn(schedulers.background())
                .subscribe(
                    ::onFetchUserByIdSuccess,
                    ::onFetchUserByIdError
                )

        disposables +=
            userRepository
                .fetchUserRepositories(userId)
                .observeOn(schedulers.main())
                .subscribeOn(schedulers.background())
                .subscribe(
                    ::onFetchUserRepositoriesSuccess,
                    ::onFetchUserRepositoriesError
                )
    }

    private fun onFetchUserRepositoriesSuccess(repo: List<GitHubUserRepository>){
        viewState.showUserRepo(repo)
    }

    private fun onFetchUserRepositoriesError(error: Throwable){
        error.printStackTrace()
    }

    private fun onFetchUserByIdSuccess(user: GitHubUser) {
        viewState.showUser(user)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onFetchUserByIdError(error: Throwable) {
        viewState.showError(error.message)

        /*
         * Возвращаемся на предыдущий
         * экран в случае ошибки.
         */
        router.exit()
    }

    override fun onDestroy() {
        super.onDestroy()

        disposables.dispose()
    }

}