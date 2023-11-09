package com.example.weatherforecast

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherforecast.data.AppDatabase
import com.example.weatherforecast.data.Repository
import com.example.weatherforecast.data.WeatherApi
import com.example.weatherforecast.data.WeatherDb
import com.example.weatherforecast.entity.TestPlace
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class RepositoryUnitTests {
    private lateinit var db: AppDatabase
    private val testPlace = TestPlace("Test_name")
    private val updatedPlace = TestPlace("Updated_place")
    private lateinit var testRepo: Repository
    private lateinit var context: Context

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun doBefore() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "test-db"
        )
            .allowMainThreadQueries()
            .build()
        testRepo = Repository(WeatherDb(db.getPlaceDao()), WeatherApi())
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testApi() {
        runBlocking {
            val place = testRepo.getPlace("Moscow")
            Assert.assertEquals("Moscow", place!!.location.name)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testDb1() {
        runBlocking {
            testRepo.addPlaceToDb(testPlace)
            Assert.assertEquals("Test_name", testRepo.getPlace("Test_name")!!.location.name)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testDb2() {
        runBlocking {
            launch {
                testRepo.addPlaceToDb(testPlace)

                testRepo.getAllPlacesFromDb().collect {
                    it.forEach { place ->
                        if (place.name == "Test_name") {
                            testRepo.updatePlaceInDb(place.id, updatedPlace)
                            Assert.assertEquals(
                                "Updated_place",
                                testRepo.getPlace("Updated_place")!!.location.name
                            )
                            cancel()
                        }
                    }
                }
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun testDb3() {
        runBlocking {
            val place = testRepo.getPlace("Moscow")
            testRepo.addPlaceToDb(place!!)
            Assert.assertEquals("Moscow", testRepo.getPlace("Moscow")!!.location.name)
        }
    }

    @Test
    @Throws(Exception::class)
    fun dbCleanUp() {
        runBlocking {
            launch {
                testRepo.addPlaceToDb(testPlace)
                testRepo.clearDb()
                testRepo.getAllPlacesFromDb().collect {
                    Assert.assertTrue(it.isEmpty())
                    cancel()
                }
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
