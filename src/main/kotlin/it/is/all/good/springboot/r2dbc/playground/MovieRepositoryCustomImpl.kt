package it.`is`.all.good.springboot.r2dbc.playground

import org.springframework.data.r2dbc.convert.R2dbcConverter
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Flux

class MovieRepositoryCustomImpl(
    private val client: DatabaseClient,
    private val converter: R2dbcConverter,
): MovieRepositoryCustom {

    override fun findMovieWithDirectors(): Flux<MovieWithDirector> {
        val query = """
            select ${Movie.SELECTIONS}, ${Director.SELECTIONS}
            from ${Movie.TABLE}
            left join ${Director.TABLE} on ${Movie.TABLE}.director_id = ${Director.TABLE}.id
        """

        return client.sql(query).map { row, _ ->
            val movie = converter.read<Movie>(row, "${Movie.TABLE}_")
            val director = converter.read<Director>(row, "${Director.TABLE}_")
            MovieWithDirector(
                movie = movie,
                director = director,
            )
        }.all()
    }
}
