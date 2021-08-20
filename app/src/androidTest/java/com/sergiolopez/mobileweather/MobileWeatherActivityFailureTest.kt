package com.sergiolopez.mobileweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.sergiolopez.data.datasources.RemoteDataSource
import com.sergiolopez.domain.model.FailException
import com.sergiolopez.domain.model.Resource
import com.sergiolopez.domain.repository.MobileWeatherRepository
import com.sergiolopez.mobileweather.di.MobileWeatherTestRepository
import com.sergiolopez.mobileweather.di.RepositoryModule
import com.sergiolopez.mobileweather.ui.main.MobileWeatherActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
class MobileWeatherActivityFailureTest {

    private var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var okHttp: OkHttpClient

    @Inject
    lateinit var remoteDataSource: RemoteDataSource

    @BindValue
    @JvmField
    val mobileWeatherSuccessRepository: MobileWeatherRepository = MobileWeatherTestRepository(
        Resource.Failure(
            FailException.NoConnectionAvailable
        )
    )

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testRule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(ActivityScenarioRule(MobileWeatherActivity::class.java))

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun clickRefreshWeather_GenerateRandomCoordenatesAndRefreshWeather() {
        val textView = onView(withId(R.id.failureNoConnectionText))

        textView.check(matches(ViewMatchers.withText(R.string.failure_no_connection)))
    }
}