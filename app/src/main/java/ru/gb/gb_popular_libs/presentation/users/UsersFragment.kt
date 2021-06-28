package ru.gb.gb_popular_libs.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import moxy.ktx.moxyPresenter
import ru.gb.gb_popular_libs.arguments
import ru.gb.gb_popular_libs.lession1.R.layout.fragment_users
import ru.gb.gb_popular_libs.lession1.databinding.FragmentUsersBinding
import ru.gb.gb_popular_libs.data.network.NetworkStateRepository
import ru.gb.gb_popular_libs.data.user.UserRepository
import ru.gb.gb_popular_libs.data.user.model.GitHubUser
import ru.gb.gb_popular_libs.data.user.model.GitHubUserRepository
import ru.gb.gb_popular_libs.presentation.abs.AbsFragment
import ru.gb.gb_popular_libs.presentation.users.adapter.UsersAdapter
import ru.gb.gb_popular_libs.scheduler.Schedulers
import javax.inject.Inject

class UsersFragment : AbsFragment(fragment_users), UsersView, UsersAdapter.Delegate {

    companion object {

        fun newInstance(): Fragment =
            UsersFragment()
                .arguments()

    }

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var networkStateRepository: NetworkStateRepository

    @Inject
    lateinit var schedulers: Schedulers

    @Inject
    lateinit var router: Router

    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            userRepository = userRepository,
            networkStateRepository = networkStateRepository,
            router = router,
            schedulers = schedulers
        )
    }

    private var viewBinding: FragmentUsersBinding? = null

    private val usersAdapter = UsersAdapter(delegate = this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentUsersBinding
            .inflate(inflater, container, false)
            .apply {
                viewBinding = this
                viewBinding?.users?.adapter = usersAdapter
            }
            .root

    override fun showLoading() {
        viewBinding?.loading?.visibility = VISIBLE
        viewBinding?.users?.visibility = GONE
    }

    override fun showUsers(users: List<GitHubUser>) {
        usersAdapter.submitList(users)

        viewBinding?.loading?.visibility = GONE
        viewBinding?.users?.visibility = VISIBLE
    }



    override fun showError(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onUserPicked(user: GitHubUser) =
        presenter.displayUser(user)

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

}