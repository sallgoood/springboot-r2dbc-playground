package it.`is`.all.good.springboot.r2dbc.playground

import reactor.core.publisher.Flux

interface MovieRepositoryCustom {

    fun findMovieWithDirectors(): Flux<MovieWithDirector>
}
