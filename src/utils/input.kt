package utils

fun resource(name: String) = {}.javaClass.getResource("/$name")?.readText()?.trim()
    ?: error("No resource named \"$name\"")

fun String.words() = this.split(Regex("\\s+")).filter { it.isNotEmpty() }
fun String.splitByWhitespace() = this.split(Regex("([\n\r]+)\\1+"))
