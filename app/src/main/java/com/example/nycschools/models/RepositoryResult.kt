package com.example.nycschools.models

/**
 * A generic class that holds a value or error.
 * @param <T>
 */
sealed class RepositoryResult<out R> {

    data class Success<out T>(val data: T) : RepositoryResult<T>()
    data class Error(val errorString: String) : RepositoryResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[errorString=$errorString]"
        }
    }
}

/**
 * `true` if [RepositoryResult] is of type [Success] & holds non-null [Success.data].
 */
val RepositoryResult<*>.succeeded
    get() = this is RepositoryResult.Success && data != null