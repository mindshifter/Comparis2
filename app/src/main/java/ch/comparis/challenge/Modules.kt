package ch.comparis.challenge

import ch.comparis.challenge.model.CarsViewModel
import ch.comparis.challenge.model.FiltersViewModel
import ch.comparis.challenge.repo.CarsRepository
import ch.comparis.challenge.repo.LocalCarsRepository
import ch.comparis.challenge.repo.NetworkCarsRepository
import ch.comparis.challenge.repo.Repository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModules = module {
    single<Repository>(named("networkRepo")) { NetworkCarsRepository() }
    single<Repository>(named("databaseRepo")) { CarsRepository() }
    single<Repository>(named("localRepo")) { LocalCarsRepository() }
}
val viewModelModules = module {
    viewModel { CarsViewModel(get(named("localRepo"))) }
    viewModel { FiltersViewModel(get(named("localRepo"))) }
}



