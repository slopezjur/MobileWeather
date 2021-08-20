package com.sergiolopez.mobileweather

import android.view.View
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.sergiolopez.data.datasources.RemoteDataSource
import com.sergiolopez.domain.repository.MobileWeatherRepository
import com.sergiolopez.mobileweather.di.MobileWeatherTestRepository
import com.sergiolopez.mobileweather.di.RepositoryModule
import com.sergiolopez.mobileweather.ui.main.MobileWeatherActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.OkHttpClient
import org.hamcrest.Matcher
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
class MobileWeatherActivityTest {

    private var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var okHttp: OkHttpClient

    @Inject
    lateinit var remoteDataSource: RemoteDataSource

    @BindValue
    @JvmField
    val mobileWeatherRepository: MobileWeatherRepository = MobileWeatherTestRepository()

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
        val oldTemperatureText = getText(withId(R.id.weatherTime))

        onView(withId(R.id.refreshWeather)).perform(click())

        val newTemperatureText = getText(withId(R.id.weatherTime))

        assertNotEquals(oldTemperatureText, newTemperatureText)
    }

    private fun getText(matcher: Matcher<View?>?): String? {
        val stringHolder = arrayOf<String?>(null)

        onView(matcher).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "getting text from a TextView"
            }

            override fun perform(uiController: UiController?, view: View) {
                val textView = view as TextView
                stringHolder[0] = textView.text.toString()
            }
        })

        return stringHolder[0]
    }
}