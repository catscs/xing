package es.webandroid.xing.framework.local

import es.webandroid.xing.data.model.RepoDataModel
import es.webandroid.xing.data.source.LocalDataSource
import es.webandroid.xing.framework.local.base.RepoDao
import es.webandroid.xing.framework.local.mapper.toRoomRepoModel
import es.webandroid.xing.framework.local.mapper.toRepoDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomDataSource @Inject constructor(
    private val dao: RepoDao
) : LocalDataSource {

    override suspend fun isEmpty() = withContext(Dispatchers.IO) { dao.repoCount() <= 0 }

    override suspend fun saveRepositories(repositories: List<RepoDataModel>) =
        withContext(Dispatchers.IO) { dao.insertRepositories(repositories.map { it.toRoomRepoModel() }) }

    override suspend fun getRepositories() = withContext(Dispatchers.IO) { dao.getAll().map { it.toRepoDataModel() } }

    override suspend fun clearData() = withContext(Dispatchers.IO) { dao.clearData() }

}
