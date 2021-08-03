package es.webandroid.xing.data.repository

import es.webandroid.xing.core.connectivity.base.ConnectivityProvider
import es.webandroid.xing.core.functional.Either
import es.webandroid.xing.core.functional.getOrElse
import es.webandroid.xing.data.mapper.RepoMapper
import es.webandroid.xing.data.model.RepoDataModel
import es.webandroid.xing.data.source.LocalDataSource
import es.webandroid.xing.data.source.RemoteDataSource
import es.webandroid.xing.domain.entities.Repo
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RepositoryImplTest {

    private val remoteDataSource = mockk<RemoteDataSource>()
    private val localDataSource = mockk<LocalDataSource>()
    private val connectivityProvider = mockk<ConnectivityProvider>()

    private lateinit var sut: RepositoryImpl

    private lateinit var repoDataModelList: List<RepoDataModel>

    @Before
    fun setup() {
        sut = RepositoryImpl(remoteDataSource, localDataSource, RepoMapper(), connectivityProvider)
        repoDataModelList = listOf(RepoDataModel(0, "name", "login", "description", "avatar", "urlRepo", true))
    }

    @Test
    fun `when hasInternet is true then get repos from remote and save data local`() {
        // Prepare
        val repoList = listOf(Repo(0, "name", "login", "description", "avatar", "urlRepo", true))
        every { connectivityProvider.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.getRepositories(any()) } returns Either.Right(repoDataModelList)
        coEvery { localDataSource.clearData() } returns Unit
        coEvery { localDataSource.saveRepositories(repoDataModelList) } returns Unit
        // Result
        val result = runBlocking { sut.getRepositories("name") }
        // Check
        coVerifyOrder {
            remoteDataSource.getRepositories(any())
            localDataSource.clearData()
            localDataSource.saveRepositories(any())
        }
        assertEquals(repoList, result.getOrElse(emptyList()))
    }

    @Test
    fun `when hasInternet is false then get repos from local`() {
        // Prepare
        val repoList = listOf(Repo(0, "name", "login", "description", "avatar", "urlRepo", true))
        every { connectivityProvider.isNetworkAvailable() } returns false
        coEvery { localDataSource.getRepositories() } returns this.repoDataModelList
        // Result
        val result = runBlocking { sut.getRepositories("name") }
        // Check
        coVerify { localDataSource.getRepositories() }
        verify { remoteDataSource wasNot Called }
        assertEquals(repoList, result.getOrElse(emptyList()))
    }

    @Test
    fun `when error db then get repos from local`() {
        // Prepare data
        every { connectivityProvider.isNetworkAvailable() } returns false
        coEvery { localDataSource.getRepositories() }.throws(Exception())
        coEvery { localDataSource.isEmpty() } returns false
        // Result
        val result = runBlocking { sut.getRepositories("name") }
        // Check
        assert(result.isLeft)
    }

}
