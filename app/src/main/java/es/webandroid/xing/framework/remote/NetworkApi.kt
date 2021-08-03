package es.webandroid.xing.framework.remote

import es.webandroid.xing.framework.remote.model.RepoResponseDataJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {

    companion object {
        const val REPORTS = "{name}/repos"
        const val PARAM_NAME = "name"
    }

    @GET(REPORTS)
    suspend fun getRepository(
        @Path(PARAM_NAME) name: String
    ): Response<List<RepoResponseDataJson>>
}
