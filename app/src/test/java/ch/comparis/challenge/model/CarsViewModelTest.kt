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
    fun `Check car list is non null` () {
        assertThat(carsViewModel.cars, notNullValue())

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }

}