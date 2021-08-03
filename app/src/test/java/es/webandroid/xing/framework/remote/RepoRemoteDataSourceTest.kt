package es.webandroid.xing.framework.remote

import es.webandroid.xing.core.error_handling.Failure.NetworkError.*
import es.webandroid.xing.core.functional.getOrElse
import es.webandroid.xing.data.model.RepoDataModel
import es.webandroid.xing.framework.remote.mapper.RepoDataMapper
import es.webandroid.xing.framework.remote.model.Owner
import es.webandroid.xing.framework.remote.model.RepoResponseDataJson
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RepoRemoteDataSourceTest {

    private val networkApi = mockk<NetworkApi>()

    private lateinit var sut: RepoRemoteDataSource

    @Before
    fun setup() {
        sut = RepoRemoteDataSource(networkApi, RepoDataMapper())
    }

    @Test
    fun `when call remote get repos`() {
        // Prepare
        val repoList = listOf(RepoDataModel(0, "name", "login", "description", "avatar", "urlRepo", true))
        val repoJson = Response.success(
            listOf(RepoResponseDataJson(0, "name", Owner("login", "avatar"), "description", "urlRepo", true))
        )
        coEvery { networkApi.getRepository(any()) } returns repoJson

        // Result
        val result = runBlocking { sut.getRepositories("name") }

        // Check
        Assert.assertEquals(repoList, result.getOrElse(emptyList()))
    }

    @Test
    fun `when call remote get repos then error 500`() {
        // Prepare
        coEvery { networkApi.getRepository(any()) } returns Response.error(500, "Server error".toResponseBody("Server error".toMediaTypeOrNull()))

        // Result
        val result = runBlocking { sut.getRepositories("name") }

        // Check
        assert(result.isLeft)
        result.fold(fnL = {
            assertTrue(it is Recoverable)
        }, {})
    }

    @Test
    fun `when call remote get repos then error 400`() {
        // Prepare
        coEvery { networkApi.getRepository(any()) } returns Response.error(400, "Error Client".toResponseBody("Error Client".toMediaTypeOrNull()))

        // Result
        val result = runBlocking { sut.getRepositories("name") }

        // Check
        assert(result.isLeft)
        result.fold(fnL = {
            assertTrue(it is Fatal)
        }, {})
    }
}