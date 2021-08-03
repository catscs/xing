package es.webandroid.xing.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.webandroid.xing.core.connectivity.base.ConnectivityProvider
import es.webandroid.xing.data.mapper.RepoMapper
import es.webandroid.xing.data.repository.RepositoryImpl
import es.webandroid.xing.data.source.LocalDataSource
import es.webandroid.xing.data.source.RemoteDataSource
import es.webandroid.xing.domain.repository.Repository
import es.webandroid.xing.domain.use_cases.RepoUseCase
import es.webandroid.xing.framework.local.RoomDataSource
import es.webandroid.xing.framework.local.base.RepoDao
import es.webandroid.xing.framework.local.base.RepoDatabase
import es.webandroid.xing.framework.remote.NetworkApi
import es.webandroid.xing.framework.remote.NetworkServiceProvider
import es.webandroid.xing.framework.remote.RepoRemoteDataSource
import es.webandroid.xing.framework.remote.mapper.RepoDataMapper
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ConnectivityModule {
    @Provides
    @Singleton
    fun connectivityProvider(@ApplicationContext context: Context): ConnectivityProvider =
        ConnectivityProvider.createProvider(context)
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun repoService(): NetworkApi = NetworkServiceProvider().networkApi()
}

@Module
@InstallIn(SingletonComponent::class)
class MappersModule {
    @Provides
    @Singleton
    fun repoDataMapper(): RepoDataMapper = RepoDataMapper()

    @Provides
    @Singleton
    fun repoMapper(): RepoMapper = RepoMapper()
}

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        repoMapper: RepoMapper,
        connectivityProvider: ConnectivityProvider
    ): Repository = RepositoryImpl(remoteDataSource, localDataSource, repoMapper, connectivityProvider)
}

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {
    @Provides
    fun providerRepoUseCase(repository: Repository): RepoUseCase = RepoUseCase(repository)
}

@Module
@InstallIn(SingletonComponent::class)
object FrameworkModule {

    @Provides
    @Singleton
    fun providerRemoteDataSource(
        networkApi: NetworkApi,
        repoDataMapper: RepoDataMapper
    ): RemoteDataSource = RepoRemoteDataSource(networkApi, repoDataMapper)

    @Provides
    @Singleton
    fun provideRepoDatabase(@ApplicationContext context: Context): RepoDatabase = RepoDatabase.build(context)

    @Provides
    @Singleton
    fun provideRoomDataSource(repoDao: RepoDao): LocalDataSource = RoomDataSource(repoDao)

    @Provides
    fun provideRepoDao(repoDB: RepoDatabase): RepoDao = repoDB.RepoDao()
}
