package es.webandroid.xing.presentation.list

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import es.webandroid.xing.R
import es.webandroid.xing.core.di.DataModule
import es.webandroid.xing.core.error_handling.Failure
import es.webandroid.xing.core.error_handling.Failure.NetworkError.Fatal
import es.webandroid.xing.core.functional.Either
import es.webandroid.xing.core.platform.MainActivity
import es.webandroid.xing.domain.entities.Repo
import es.webandroid.xing.domain.repository.Repository
import es.webandroid.xing.fake.FakeRepoData
import es.webandroid.xing.presentation.list.SearchViewActionExtension.Companion.submitText
import es.webandroid.xing.presentation.list.SearchViewActionExtension.Companion.typeText
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Singleton

@UninstallModules(DataModule::class)
@HiltAndroidTest
class RepoListTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Module
    @InstallIn(SingletonComponent::class)
    class DataModule {

        companion object {
            lateinit var response: Either<Failure, List<Repo>>
        }

        @Provides
        @Singleton
        fun provideRepository(): Repository {
            return object : Repository {
                override suspend fun getRepositories(name: String): Either<Failure, List<Repo>> {
                    return response
                }
            }
        }
    }

    @Before
    fun setup() {
        DataModule.response = Either.Right(FakeRepoData.repos)
    }

    @Test
    fun shouldDisplayTheRecycler() {
        launchActivity<MainActivity>()
        onView(withId(R.id.recycler)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun shouldDisplayTheListAndScroll() {
        launchActivity<MainActivity>()
        onView(withId(R.id.recycler))
            .perform(actionOnItemAtPosition<RepoListAdapter.ViewHolder>(FakeRepoData.repos.size - 1, scrollTo()));
    }

    @Test
    fun shouldClickItemRecycler() {
        launchActivity<MainActivity>()
        onView(withId(R.id.recycler)).perform(actionOnItemAtPosition<RepoListAdapter.ViewHolder>(0, longClick()))
        onView(withText(R.string.title_modal))
        onView(withText(R.string.button_go_modal)).perform(click())
    }

    @Test
    fun shouldWriteAndSendFromSearchView() {
        launchActivity<MainActivity>()
        onView(withId(R.id.search_view)).perform(typeText("Mozilla"))
        onView(withId(R.id.search_view)).perform(submitText("Mozilla"))
    }

    @Test
    fun shouldDisplayMessageOnErrorRepository() {
        DataModule.response = Either.Left(Fatal("Server error"))
        launchActivity<MainActivity>()
        onView(withId(R.string.failure_fatal_error))
    }
}
