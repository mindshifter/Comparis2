package ch.comparis.challenge.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ch.comparis.challenge.repo.CarsRepository
import ch.comparis.challenge.TestApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
internal class FiltersViewModelTest : KoinTest {
    private val carsRepository: CarsRepository by inject()
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


    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }

}