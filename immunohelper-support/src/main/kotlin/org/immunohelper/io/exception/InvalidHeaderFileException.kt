package org.immunohelper.io.exception

class InvalidHeaderFileException : RuntimeException {

    constructor(message: String) : super(message)
    constructor(message: String, ex: Throwable) : super(message, ex)

}