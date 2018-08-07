package net.thiki.rest.sample.exception

class AssertionException(message: String?, cause: Throwable? = null, val code: Int = 0) : Throwable(message, cause)
