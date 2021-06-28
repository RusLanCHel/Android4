package ru.gb.gb_popular_libs.data.user.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = GitHubUser::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    tableName = "github_user_repositories")
data class GitHubUserRepository(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @ColumnInfo(name = "user_id")
    @Transient
    val userId: Int,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,
)