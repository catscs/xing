package es.webandroid.xing.framework.remote

import es.webandroid.xing.core.error_handling.Failure
import es.webandroid.xing.core.error_handling.Failure.NetworkError.Fatal
import es.webandroid.xing.core.error_handling.Failure.NetworkError.Recoverable
import es.webandroid.xing.core.functional.Either
import es.webandroid.xing.data.model.RepoDataModel
import es.webandroid.xing.data.source.RemoteDataSource
import es.webandroid.xing.framework.remote.mapper.RepoDataMapper
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import javax.inject.Inject

class RepoRemoteDataSource @Inject constructor(
    private val networkApi: NetworkApi,
    private val repoDataMapper: RepoDataMapper
) : RemoteDataSource {

    override suspend fun getRepositories(name: String): Either<Failure, List<RepoDataModel>> = with(Dispatchers.IO) {
        val response = networkApi.getRepository(name)
        handlerResponse(
            response,
            data = response.body(),
            transform = { it.map { repo -> repoDataMapper.mapTo(repo) } },
            default = emptyList()
        )
    }

    private fun <Json, D, R> handlerResponse(response: Response<Json>, data: D?, transform: (D) -> R, default: D): Either<Failure, R> {
        return when (response.isSuccessful) {
            true -> Either.Right(transform(data ?: default))
            false -> Either.Left(getNetworkError(response.code(), response.message()))
        }
    }

    private fun getNetworkError(statusCode: Int, message: String?): Failure {
        return when (statusCode) {
            in 400..499 -> Fatal(message)
            else -> Recoverable(message)
        }
    }
}
