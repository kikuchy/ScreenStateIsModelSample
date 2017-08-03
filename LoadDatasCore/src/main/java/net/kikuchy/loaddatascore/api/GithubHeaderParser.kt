package net.kikuchy.loaddatascore.api

/**
 * Created by hiroshi.kikuchi on 2017/08/02.
 */
object GithubHeaderParser {
    val PATTERN = Regex("<https://api\\.github\\.com/.+\\?page=(\\d+)>; rel=\"(next|last)\"")

    fun parse(link: String): Result {
        var page = -1
        var type: Type? = null
        PATTERN.find(link)?.groupValues?.let {
            page = it[1].toInt()
            type = when (it[2]) {
                "next" -> Type.NEXT
                "last" -> Type.LAST
                else -> throw IllegalStateException()
            }
        }
        return Result(type!!, page)
    }

    data class Result(val rel: Type, val page: Int)

    enum class Type {
        NEXT,
        LAST
    }
}