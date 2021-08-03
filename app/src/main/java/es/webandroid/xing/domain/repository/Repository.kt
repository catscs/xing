package es.webandroid.xing.domain.repository

import es.webandroid.xing.core.error_handling.Failure
import es.webandroid.xing.core.functional.Either
import es.webandroid.xing.domain.entities.Repo

interface Repository {
    suspend fun getRepositories(name: String): Either<Failure, List<Repo>>
}
