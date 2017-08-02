package net.kikuchy.loaddatascore

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by hiroshi.kikuchi on 2017/08/02.
 */
class ResultTest {
    @org.junit.Test
    fun getUnwrappedSucceeded() {
        val success = Result.Success("hoge")

        assertEquals("hoge", success.unwrapped)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUnwrappedFailured() {
        val failure = Result.Failure(IllegalArgumentException())

        failure.unwrapped
    }

    @org.junit.Test
    fun isSuccess() {
        val success = Result.Success("hoge")
        val failure = Result.Failure(IllegalArgumentException())

        assert(success.isSuccess)
        assertFalse(failure.isSuccess)
    }

}