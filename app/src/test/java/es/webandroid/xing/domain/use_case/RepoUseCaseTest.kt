package es.webandroid.xing.domain.use_case

import es.webandroid.xing.core.functional.Either
import es.webandroid.xing.core.functional.getOrElse
import es.webandroid.xing.domain.entities.Repo
import es.webandroid.xing.domain.repository.Repository
import es.webandroid.xing.domain.use_cases.RepoUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RepoUseCaseTest {

    private val repository = mockk<Repository>()

    private lateinit var sut: RepoUseCase

    @Before
    fun setup() {
        sut = RepoUseCase(repository)
    }

    @Test
    fun `when we call use case then get from repository data`() {
        // Prepare
        val repoList = listOf(Repo(0, "name", "login", "description", "avatar", "urlRepo", true))
        val params = RepoUseCase.Params("name")
        coEvery { repository.getRepositories(params.name) } returns Either.Right(repoList)
        // Result
        val result = runBlocking { sut.run(params) }
        // Check
        coVerify { repository.getRepositories(any()) }
        assertEquals(repoList, result.getOrElse(emptyList()))
    }
}
