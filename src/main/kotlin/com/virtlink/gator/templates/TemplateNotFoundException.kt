package com.virtlink.gator.templates

class TemplateNotFoundException(
        val name: String,
        val path: String?,
        message: String?,
        cause: Throwable?
) : RuntimeException(getMessage(message, name, path), cause) {

    companion object {
        fun getMessage(message: String?, name: String, path: String?): String {
            return when {
                message != null -> message
                path != null -> "Template $name not found in $path."
                else -> "Template $name not found."
            }
        }
    }

    constructor(name: String) : this(name, null, null, null)
    constructor(name: String, path: String?) : this(name, path, null, null)
    constructor(name: String, path: String?, message: String?) : this(name, path, message, null)

}