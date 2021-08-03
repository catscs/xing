package es.webandroid.xing.domain.use_cases

import es.webandroid.xing.domain.entities.Repo
import es.webandroid.xing.domain.repository.Repository
import es.webandroid.xing.domain.use_cases.RepoUseCase.Params
import javax.inject.Inject

class RepoUseCase @Inject constructor(private val repository: Repository) : UseCase<List<Repo>, Params>() {

    override suspend fun run(params: Params) = repository.getRepositories(params.name)

    data class Params(
        val name: String
    )
}
