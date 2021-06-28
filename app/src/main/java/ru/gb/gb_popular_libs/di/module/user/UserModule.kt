package ru.gb.gb_popular_libs.di.module.user

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.gb.gb_popular_libs.di.Cache
import ru.gb.gb_popular_libs.di.Cloud
import ru.gb.gb_popular_libs.di.InMemory
import ru.gb.gb_popular_libs.di.Persisted
import ru.gb.gb_popular_libs.di.module.network.NetworkModule
import ru.gb.gb_popular_libs.data.api.GitHubApi
import ru.gb.gb_popular_libs.data.storage.GitHubStorage
import ru.gb.gb_popular_libs.data.storage.migration.GitHub1to2Migration
import ru.gb.gb_popular_libs.data.storage.migration.GitHub2to3Migration
import ru.gb.gb_popular_libs.data.user.UserRepository
import ru.gb.gb_popular_libs.data.user.UserRepositoryImpl
import ru.gb.gb_popular_libs.data.user.datasource.UserDataSource
import ru.gb.gb_popular_libs.data.user.datasource.cache.CacheUserDataSource
import ru.gb.gb_popular_libs.data.user.datasource.cache.CacheUserDataSourceImpl
import ru.gb.gb_popular_libs.data.user.datasource.cloud.CloudUserDataSource
import javax.inject.Singleton

@Module(includes = [UserUiModule::class, NetworkModule::class])
class UserModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        @Cloud cloudUserDataSource: UserDataSource,
        @Cache cacheUserDataSource: CacheUserDataSource
    ): UserRepository =
        UserRepositoryImpl(
            cloudUserDataSource,
            cacheUserDataSource
        )

    @Cloud
    @Singleton
    @Provides
    fun provideCloudUserDataSource(gitHubApi: GitHubApi): UserDataSource =
        CloudUserDataSource(gitHubApi)

    @Cache
    @Singleton
    @Provides
    fun provideCacheUserDataSource(
        @Persisted gitHubStorage: GitHubStorage
    ): CacheUserDataSource =
        CacheUserDataSourceImpl(gitHubStorage)

    @InMemory
    @Singleton
    @Provides
    fun provideInMemoryGitHubStorage(context: Context): GitHubStorage =
        Room.inMemoryDatabaseBuilder(context, GitHubStorage::class.java)
            .fallbackToDestructiveMigration()
            .build()

    @Persisted
    @Singleton
    @Provides
    fun provideDatabaseGitHubStorage(context: Context): GitHubStorage =
        Room.databaseBuilder(context, GitHubStorage::class.java, "github.db")
            .addMigrations(
                GitHub1to2Migration,
                GitHub2to3Migration
            )
            .build()

}