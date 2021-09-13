package it.`is`.all.good.springboot.r2dbc.playground

import it.`is`.all.good.springboot.r2dbc.playground.Director.Companion.TABLE
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import javax.annotation.Generated
import kotlin.reflect.full.declaredMemberProperties

@Table(TABLE)
data class Director(
    @Id
    var id: Long? = null,
    val name: String,
) {
    companion object {
        const val TABLE = "director"
        val COLUMNS = Director::class.declaredMemberProperties.map { it.name.camelToSnakeCase() }
        val SELECTIONS = COLUMNS.joinToString(",") { "${TABLE}.$it as ${TABLE}_$it" }
    }
}
