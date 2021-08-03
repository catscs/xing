package es.webandroid.xing.framework.remote.mapper

import es.webandroid.xing.data.model.RepoDataModel
import es.webandroid.xing.framework.remote.model.RepoResponseDataJson


interface MapperTo<in T, out R> {
    fun mapTo(t: T): R
}

class RepoDataMapper : MapperTo<RepoResponseDataJson, RepoDataModel> {
    override fun mapTo(t: RepoResponseDataJson) =
        RepoDataModel(
            t.id,
            t.name,
            t.owner.login,
            t.description ?: "",
            t.owner.avatar_url,
            t.html_url,
            t.fork
        )
}
