package ch.comparis.challenge.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ch.comparis.challenge.TestApplication
import ch.comparis.challenge.repo.CarsRepository
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
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
internal class FiltersViewModelTest : KoinTest {
     private val carsRepository =  mock(CarsRepository::class.java)
    private lateinit var filtersViewModel: FiltersViewModel

    private val mainThreadSurrogate = TestCoroutineDispatcher()


    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    @Before
    fun init() {
        Dispatchers.setMain(mainThreadSurrogate)
        filtersViewModel = FiltersViewModel(carsRepository)
    }

    @Test
    fun `test if get all makes`() {
       val carMakes = carsRepository.fetchCars().map { it.make }.toMutableList()
        assertThat(carMakes, notNullValue())
    }

    @Test
    fun resetFilterTest() {
        filtersViewModel.updateFilter(CarsFilter())
        assertNotNull(CarsFilter())
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }

}