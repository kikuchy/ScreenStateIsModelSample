package net.kikuchy.loaddatascore

/**
 * Created by hiroshi.kikuchi on 2017/08/02.
 */
sealed class Result<out T, out E : Throwable> {
    data class Success<out T>(val value: T) : Result<T, Nothing>()
    data class Failure<out E : Throwable>(val error: E) : Result<Nothing, E>()

    val unwrapped: T
        get() = when (this) {
            is Success -> value
            is Failure -> throw error
        }

    val isSuccess: Boolean
        get() = when (this) {
            is Success -> true
            is Failure -> false
        }
}

inline fun <T, E : Throwable, U> Result<T, E>.fold(suc: (T) -> U, fai: (E) -> U): U =
        when (this) {
            is Result.Success -> suc(value)
            is Result.Failure -> fai(error)
        }

inline fun <T, E : Throwable, U> Result<T, E>.flatMapSuccess(f: (T) -> Result<U, E>): Result<U, E> =
        fold(f, { this as Result.Failure })

inline fun <T, E : Throwable, U> Result<T, E>.mapSuccess(f: (T) -> U): Result<U, E> =
        when (this) {
            is Result.Success -> Result.Success(f(value))
            is Result.Failure -> this
        }