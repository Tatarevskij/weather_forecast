package com.example.weatherforecast

import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.weatherforecast.presentation.MainActivity
import com.example.weatherforecast.screen.CitiesScreen
import com.example.weatherforecast.screen.DetailsScreen
import com.example.weatherforecast.screen.MainScreen
import com.example.weatherforecast.screen.SearchScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test


class UITests : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun interfaceTest1() =
        run {
            step("1 step") {
                MainScreen {
                    citiesLayoutBtn {
                        isVisible()
                        click()
                    }
                    device.screenshots.take("test Screenshot")
                }
            }
        }

    @Test
    fun interfaceTest2() =
        run {
            step("1 step") {
                MainScreen {
                    SearchScreen {
                        textInput {
                            flakySafely(timeoutMs = 7000) { isVisible() }
                            replaceText("Moscow")
                        }
                        searchBtn{
                            isVisible()
                            click()
                        }
                    }
                    DetailsScreen {
                        placeName{
                            flakySafely(timeoutMs = 2000) { isVisible() }
                            hasText("Moscow")
                        }
                    }
                }
            }
        }

    @Test
    fun interfaceTest3() =
        run {
            step("step 1") {
                MainScreen{
                    citiesLayoutBtn{
                        isVisible()
                        click()
                    }
                    CitiesScreen{
                        clearBtn{
                            isVisible()
                            click()
                        }
                    }
                }
            }
            step("step 2"){
                MainScreen{
                    CitiesScreen{
                        savedPlaces{
                            hasSize(0)
                        }
                    }
                }
            }
        }

}





