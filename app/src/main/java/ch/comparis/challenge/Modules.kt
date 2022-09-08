package ch.comparis.challenge

import ch.comparis.challenge.model.CarsViewModel
import ch.comparis.challenge.model.FiltersViewModel
import ch.comparis.challenge.repo.CarsRepository
import ch.comparis.challenge.repo.Repository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModules = module {
    single<Repository> { CarsRepository() }
}
val viewModelModules = module {
    viewModel { CarsViewModel(get()) }
    viewModel { FiltersViewModel(get()) }
}



