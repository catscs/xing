package es.webandroid.xing.data.source

import es.webandroid.xing.core.error_handling.Failure
import es.webandroid.xing.core.functional.Either
import es.webandroid.xing.data.model.RepoDataModel

interface RemoteDataSource {
    suspend fun getRepositories(name: String): Either<Failure, List<RepoDataModel>>
}
