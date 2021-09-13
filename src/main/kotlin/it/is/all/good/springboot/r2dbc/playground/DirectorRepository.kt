package it.`is`.all.good.springboot.r2dbc.playground

import org.springframework.data.repository.reactive.ReactiveSortingRepository

interface DirectorRepository: ReactiveSortingRepository<Director, Long> {
}
