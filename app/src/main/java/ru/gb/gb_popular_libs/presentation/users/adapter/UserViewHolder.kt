package ru.gb.gb_popular_libs.presentation.users.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.gb.gb_popular_libs.click
import ru.gb.gb_popular_libs.lession1.databinding.ViewUserBinding
import ru.gb.gb_popular_libs.data.user.model.GitHubUser
import ru.gb.gb_popular_libs.presentation.users.adapter.UsersAdapter.Delegate
import ru.gb.gb_popular_libs.setCircleImageFromUri

class UserViewHolder(
    private val viewBinding: ViewUserBinding
): RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(user: GitHubUser, delegate: Delegate?) {
        with(viewBinding) {
            userAvatar.setCircleImageFromUri(user.avatar)
            userId.text = user.name ?: user.login

            root.click { delegate?.onUserPicked(user) }
        }
    }

}