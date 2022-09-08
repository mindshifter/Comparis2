package ch.comparis.challenge

import ch.comparis.challenge.repo.CarsRepository
import ch.comparis.challenge.repo.Repository
import org.koin.dsl.module

val testRepositoryModules = module {
        single<Repository> { CarsRepository() }
}



