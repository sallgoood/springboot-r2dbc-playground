package it.`is`.all.good.springboot.r2dbc.playground

import it.`is`.all.good.springboot.r2dbc.playground.Utils.generateCompressedUUID
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class MovieRepositoryTest {

    @Autowired
    lateinit var directors: DirectorRepository

    @Autowired
    lateinit var movies: MovieRepository

    @Test
    fun findMovieWithDirectors() {
        runBlocking {
            val destinDanielCretton = Director(
                name = "Destin Daniel Cretton"
            )

            val savedDirectors = directors.saveAll(listOf(destinDanielCretton)).collectList().awaitSingle()
                .associateBy { it.name }

            val shangChi = Movie(
                name = "Shang-Chi and the Legend of the Ten Rings",
                directorId = savedDirectors["Destin Daniel Cretton"]!!.id!!,
            )

            val justMercy = Movie(
                name = "Just Mercy",
                directorId = savedDirectors["Destin Daniel Cretton"]!!.id!!,
            )

            val theShack = Movie(
                name = "The Shack",
                directorId = savedDirectors["Destin Daniel Cretton"]!!.id!!,
            )

            val directorAndMovies = mapOf(
                destinDanielCretton to listOf(shangChi, justMercy, theShack)
            )

            movies.saveAll(directorAndMovies.values.flatten()).collectList().awaitSingle()
            val movieWithDirectors = movies.findMovieWithDirectors().collectList().awaitSingle()
            assertAll(
                { assertEquals(3, movieWithDirectors.size) },
                { assertEquals(destinDanielCretton, movieWithDirectors[0].director) },
                { assertEquals(shangChi.copy(id = null), movieWithDirectors[0].movie.copy(id = null)) },
                { assertEquals(justMercy.copy(id = null), movieWithDirectors[1].movie.copy(id = null)) },
                { assertEquals(theShack.copy(id = null), movieWithDirectors[2].movie.copy(id = null)) },
            )
        }
    }
}
