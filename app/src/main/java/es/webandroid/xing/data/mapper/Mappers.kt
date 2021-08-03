package es.webandroid.xing.data.mapper

import es.webandroid.xing.data.model.RepoDataModel
import es.webandroid.xing.domain.entities.Repo

interface MapperTo<in T, out R> {
    fun mapTo(t: T): R
}

class RepoMapper : MapperTo<RepoDataModel, Repo> {
    override fun mapTo(t: RepoDataModel) =
        Repo(
            t.id,
            t.name,
            t.login,
            t.description,
            t.avatar,
            t.urlRepo,
            t.fork
        )
}
