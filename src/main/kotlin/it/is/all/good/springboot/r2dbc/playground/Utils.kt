package it.`is`.all.good.springboot.r2dbc.playground

import io.r2dbc.spi.Row
import org.springframework.data.r2dbc.convert.R2dbcConverter
import org.springframework.util.DigestUtils
import java.util.*

object Utils {

    fun generateCompressedUUID() = DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().toByteArray())
}

val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()

fun String.camelToSnakeCase(): String {
    return camelRegex.replace(this) {
        "_${it.value}"
    }.lowercase(Locale.getDefault())
}

inline fun <reified T> R2dbcConverter.read(row: Row, aliasPrefix: String): T = read(
    T::class.java,
    object : Row {
        override fun <T : Any?> get(index: Int, type: Class<T>) = row[index, type]
        override fun <T : Any?> get(name: String, type: Class<T>) = row["$aliasPrefix$name", type]
    }
)
