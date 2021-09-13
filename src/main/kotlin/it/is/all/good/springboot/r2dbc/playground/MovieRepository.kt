package it.`is`.all.good.springboot.r2dbc.playground

import org.springframework.data.repository.reactive.ReactiveSortingRepository

interface MovieRepository: ReactiveSortingRepository<Movie, Long>, MovieRepositoryCustom
