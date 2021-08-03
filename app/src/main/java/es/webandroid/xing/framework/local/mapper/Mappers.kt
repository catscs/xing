package es.webandroid.xing.framework.local.mapper

import es.webandroid.xing.data.model.RepoDataModel
import es.webandroid.xing.framework.local.model.RepoModel

fun RepoDataModel.toRoomRepoModel(): RepoModel =
    RepoModel(
        id,
        name,
        login,
        description,
        urlRepo,
        avatar,
        fork
    )

fun RepoModel.toRepoDataModel(): RepoDataModel =
    RepoDataModel(
        id,
        name,
        login,
        description,
        urlRepo,
        avatar,
        fork
    )
