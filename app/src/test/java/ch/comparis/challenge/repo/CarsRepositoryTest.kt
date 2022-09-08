package ch.comparis.challenge.repo

import com.muume.cabinetto.TestApplication
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
internal class CarsRepositoryTest : KoinTest {
    private val carsRepository: CarsRepository by inject()


    @Test
    fun fetchCars() {
    }

    @Test
    fun filterByFavorite() {
    }

    @Test
    fun filterByMileage() {
    }
    @Test
    fun filterByMakes() {
    }
}