package es.webandroid.xing.presentation.list

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.karumi.shot.ActivityScenarioUtils.waitForActivity
import com.karumi.shot.ScreenshotTest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import es.webandroid.xing.core.di.DataModule
import es.webandroid.xing.core.error_handling.Failure
import es.webandroid.xing.core.functional.Either
import es.webandroid.xing.core.platform.MainActivity
import es.webandroid.xing.domain.entities.Repo
import es.webandroid.xing.domain.repository.Repository
import es.webandroid.xing.fake.FakeRepoData
import org.junit.Rule
import org.junit.Test
import javax.inject.Singleton

@UninstallModules(DataModule::class)
@HiltAndroidTest
class RepoListScreenshotTest: ScreenshotTest {
    @get:Rule
    var rule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Module
    @InstallIn(SingletonComponent::class)
    class DataModule {
        @Provides
        @Singleton
        fun provideRepository(): Repository {
            return object : Repository {
                override suspend fun getRepositories(name: String): Either<Failure, List<Repo>> {
                    return Either.Right(FakeRepoData.repos)
                }
            }
        }
    }

    @Test
    fun listRepoWhenJustStarted() {
        val activity = rule.scenario.waitForActivity()
        compareScreenshot(activity)
    }
}
