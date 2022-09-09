package ch.comparis.challenge.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ch.comparis.challenge.repo.CarsRepository
import ch.comparis.challenge.TestApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
internal class CarsViewModelTest : KoinTest {

    private val carsRepository = Mockito.mock(CarsRepository::class.java)
    private lateinit var carsViewModel: CarsViewModel

    private val mainThreadSurrogate = TestCoroutineDispatcher()

    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    @Before
    fun init() {
        Dispatchers.setMain(mainThreadSurrogate)
        carsViewModel = CarsViewModel(carsRepository)
    }

    @Test
    fun `Check LiveData is non null` () {
        assertThat(carsViewModel.cars.value, notNullValue())
    }

    @Test
    fun addToFavoriteTest() {
        val car = Car(Make(1,"Audi"),"https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2021AUC280001/2021AUC28000101.jpg", 18000)
        carsRepository.addCarToFavorite(car.make.name)
        assertNotNull(carsRepository.addCarToFavorite("Audi"))
    }

    @Test
    fun `test if add to favorites`() {
        val filter = CarsFilter()
        var cacheCars = carsRepository.fetchCars()
        if (filter.showFavorite) {
            cacheCars = cacheCars.filter { it.isFavorite }
            assertThat(cacheCars, notNullValue())
        }
    }

    @Test
    fun `test cars filter by make`() {
        val filter = CarsFilter()
        var cacheCars = carsRepository.fetchCars()
        if (filter.makes!!.isNotEmpty()) {
            cacheCars = cacheCars.filter { it.make.isSelected }
            assertNotNull(cacheCars)
        }
    }

    @Test
    fun `test cars filter by mileage`() {
        val mileageFrom = 1000
        val mileageTo = 35600
        var cacheCars = carsRepository.fetchCars()
        cacheCars = cacheCars.filter { it.mileage in mileageFrom..mileageTo }
        assertThat(cacheCars, notNullValue())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }

}