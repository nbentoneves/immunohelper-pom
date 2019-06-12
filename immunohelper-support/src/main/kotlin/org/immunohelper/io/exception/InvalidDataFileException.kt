package org.immunohelper.io.exception

class InvalidDataFileException : RuntimeException {

    constructor(message: String) : super(message)
    constructor(message: String, ex: Throwable) : super(message, ex)
    
}