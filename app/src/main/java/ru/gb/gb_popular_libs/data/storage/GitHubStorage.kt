package ru.gb.gb_popular_libs.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.gb.gb_popular_libs.data.storage.user.GitHubUserDao
import ru.gb.gb_popular_libs.data.user.model.GitHubUser

@Database(entities = [GitHubUser::class], version = 3)
abstract class GitHubStorage : RoomDatabase() {

    abstract fun gitHubUserDao(): GitHubUserDao

}