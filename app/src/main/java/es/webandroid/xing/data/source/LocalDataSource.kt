package es.webandroid.xing.data.source

import es.webandroid.xing.data.model.RepoDataModel

interface LocalDataSource {
    suspend fun getRepositories(): List<RepoDataModel>
    suspend fun isEmpty(): Boolean
    suspend fun saveRepositories(repositories: List<RepoDataModel>)
    suspend fun clearData()
}
