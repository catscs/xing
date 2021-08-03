package es.webandroid.xing.presentation.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.webandroid.xing.CoroutinesTestRule
import es.webandroid.xing.core.error_handling.Failure
import es.webandroid.xing.core.functional.Either
import es.webandroid.xing.domain.entities.Repo
import es.webandroid.xing.domain.repository.Repository
import es.webandroid.xing.domain.use_cases.RepoUseCase
import es.webandroid.xing.presentation.list.RepoListViewModel.Event
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RepoListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var sut: RepoListViewModel

    private val repoUseCase = spyk(RepoUseCase(mockk(Repository::class.java.name)))

    @Before
    fun setup() {
        sut = RepoListViewModel(repoUseCase)
    }

    @Test
    fun `when call repoUserCase shoots event Container`() {
        // Prepare
        val repos = listOf(Repo(0, "name", "login", "dess", "avatar", "urlRepo", false))
        coEvery { repoUseCase.run(any()) } returns Either.Right(repos)

        // Result
        sut.getRepositories("name")

        // Check
        sut.event.observeForever {
            it.consume { event ->
                when (event) {
                    is Event.Content -> {
                        Assert.assertEquals(1, event.repositories.size)
                        Assert.assertEquals(repos, event.repositories)
                    }
                    else -> Assert.assertTrue(false)
                }
            }
        }
    }

    @Test
    fun `when call onRepoClicked shoots event Detail`() {
        // Prepare
        val url = "http://github.com"

        // Result
        sut.onRepoClicked(url)

        // Check
        sut.event.observeForever {
            it.consume { event ->
                when (event) {
                    is Event.Detail -> Assert.assertEquals(url, event.urlRepo)
                    else -> Assert.assertTrue(false)
                }
            }
        }
    }

    @Test
    fun `when call repoUserCase shoots event Error Fatal`() {
        // Prepare
        val msg = "Server error"
        coEvery { repoUseCase.run(any()) } returns Either.Left(Failure.NetworkError.Fatal(msg))

        // Result
        sut.getRepositories("name")

        // Check
        sut.failure.observeForever {
            when (it) {
                is Failure.NetworkError.Fatal -> Assert.assertEquals(msg, it.reason)
                else -> Assert.assertTrue(false)
            }

        }
    }

    @Test
    fun `when call repoUserCase shoots event Error Recoverable`() {
        // Prepare
        val msg = "Recoverable"
        coEvery { repoUseCase.run(any()) } returns Either.Left(Failure.NetworkError.Recoverable(msg))

        // Result
        sut.getRepositories("name")

        // Check
        sut.failure.observeForever {
            when (it) {
                is Failure.NetworkError.Recoverable -> Assert.assertEquals(msg, it.reason)
                else -> Assert.assertTrue(false)
            }
        }
    }

}
