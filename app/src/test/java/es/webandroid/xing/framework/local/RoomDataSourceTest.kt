package es.webandroid.xing.framework.local

import es.webandroid.xing.data.model.RepoDataModel
import es.webandroid.xing.framework.local.base.RepoDao
import es.webandroid.xing.framework.local.model.RepoModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RoomDataSourceTest {

    private val repoDao = mockk<RepoDao>()

    private lateinit var sut: RoomDataSource

    @Before
    fun setup() {
        sut = RoomDataSource(repoDao)
    }

    @Test
    fun `when call remote get repos`() {
        // Prepare
        val repoModelList = listOf(RepoModel(0, "name", "login", "description", "avatar", "urlRepo", true))
        val repoDataModel = listOf(RepoDataModel(0, "name", "login", "description", "avatar", "urlRepo", true))

        coEvery { repoDao.getAll() } returns repoModelList

        // Result
        val result = runBlocking { sut.getRepositories() }

        // Check
        Assert.assertEquals(repoDataModel, result)
    }
}