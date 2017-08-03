package net.kikuchy.loaddatascore.api

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by hiroshi.kikuchi on 2017/08/03.
 */
class GithubHeaderParserTest {
    @Test
    fun parse() {
        val link = "<https://api.github.com/resource?page=2>; rel=\"next\""
        val res = GithubHeaderParser.parse(link)
        assertEquals(GithubHeaderParser.Result(GithubHeaderParser.Type.NEXT, 2), res)
    }

}