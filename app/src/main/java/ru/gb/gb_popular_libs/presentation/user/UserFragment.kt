package ru.gb.gb_popular_libs.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import moxy.ktx.moxyPresenter
import ru.gb.gb_popular_libs.arguments
import ru.gb.gb_popular_libs.lession1.R.layout.fragment_user
import ru.gb.gb_popular_libs.lession1.databinding.FragmentUserBinding
import ru.gb.gb_popular_libs.data.user.UserRepository
import ru.gb.gb_popular_libs.data.user.model.GitHubUser
import ru.gb.gb_popular_libs.data.user.model.GitHubUserRepository
import ru.gb.gb_popular_libs.presentation.abs.AbsFragment
import ru.gb.gb_popular_libs.scheduler.Schedulers
import javax.inject.Inject

class UserFragment : AbsFragment(fragment_user), UserView {

    companion object {

        private const val ARG_USER_ID = "userId"

        fun newInstance(userLogin: String): Fragment =
            UserFragment()
                .arguments(ARG_USER_ID to userLogin)

    }

    private val userId: String by lazy {
        arguments?.getString(ARG_USER_ID) ?: ""
    }

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var schedulers: Schedulers

    @Inject
    lateinit var router: Router

    @Suppress("unused")
    private val presenter: UserPresenter by moxyPresenter {
        UserPresenter(
            userId,
            userRepository = userRepository,
            schedulers = schedulers,
            router = router
        )
    }

    private var viewBinding: FragmentUserBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentUserBinding
            .inflate(inflater, container, false)
            .apply { viewBinding = this }
            .root

    override fun showUser(user: GitHubUser) {
        viewBinding?.userId?.text = user.id
        viewBinding?.userName?.text = user.name ?: user.login
    }

    override fun showUserRepo(repo: List<GitHubUserRepository>) {
        var s = ""
        repo.forEach{s += it.name}
        viewBinding?.userRepo?.text = s
    }

    override fun showError(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

}