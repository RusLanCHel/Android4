package ru.gb.gb_popular_libs.presentation.users.adapter

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.gb.gb_popular_libs.lession1.databinding.ViewUserBinding
import ru.gb.gb_popular_libs.data.user.model.GitHubUser

class UsersAdapter(private val delegate: Delegate?): ListAdapter<GitHubUser, UserViewHolder>(UserDiffing) {

    interface Delegate {

        /**
         * Событие наступает при выборе
         * пользователя из списка.
         * @param user пользователь
         */
        fun onUserPicked(user: GitHubUser)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            ViewUserBinding
                .inflate(from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(getItem(position), delegate)

}