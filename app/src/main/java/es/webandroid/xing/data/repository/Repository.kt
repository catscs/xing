package es.webandroid.xing.data.repository

import es.webandroid.xing.core.connectivity.base.ConnectivityProvider
import es.webandroid.xing.core.error_handling.Failure
import es.webandroid.xing.core.functional.Either
import es.webandroid.xing.core.functional.getOrElse
import es.webandroid.xing.data.mapper.RepoMapper
import es.webandroid.xing.data.source.LocalDataSource
import es.webandroid.xing.data.source.RemoteDataSource
import es.webandroid.xing.domain.entities.Repo
import es.webandroid.xing.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val repoMapper: RepoMapper,
    private val connectivityProvider: ConnectivityProvider,
) : Repository {

    override suspend fun getRepositories(name: String): Either<Failure, List<Repo>> {
        if (connectivityProvider.isNetworkAvailable()) {
            val response = remoteDataSource.getRepositories(name)
            localDataSource.clearData()
            localDataSource.saveRepositories(response.getOrElse(emptyList()))
            return handlerResponse(
                data = response.getOrElse(emptyList()),
                transform = { it.map { repo -> repoMapper.mapTo(repo) } },
                default = emptyList()
            )
        }

        return try {
            val repos = localDataSource.getRepositories()
            Either.Right(repos.map { repoMapper.mapTo(it) })
        } catch (e: Exception) {
            Either.Left(Failure.DBError)
        }
    }

    private fun <R, D> handlerResponse(data: D?, transform: (D) -> R, default: D) = Either.Right(transform(data ?: default))

}
