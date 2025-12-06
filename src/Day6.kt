import utils.flipped
import utils.resource
import utils.words

fun main() {
    val lines = resource("Day6.txt").lines()
    Day6(lines).printResults()
}

class Day6(lines: List<String>) : Day() {
    val horizontal = lines.dropLast(1).map { line ->
        line.words().map { it.toLongOrNull() ?: 0 }
    }.flipped()

    val vertical = lines.dropLast(1)
        .map { it.toCharArray().toList() }.flipped()
        .map { it.joinToString("").trim() }
        .fold(listOf(listOf<Long>())) { res, curr ->
            res.toMutableList().apply {
                if (curr.isEmpty()) {
                    add(listOf())
                } else {
                    val group = removeLast()
                    add(group + (curr.toLongOrNull() ?: 0))
                }
            }
        }

    val operators: List<Long.(Long) -> Long> = lines.last().words().map {
        when (it) {
            "+" -> Long::plus
            "*" -> Long::times
            else -> throw Exception("Unexpected operator $it")
        }
    }

    override fun part1() = horizontal.withIndex().sumOf {
        it.value.reduce(operators[it.index])
    }

    override fun part2() = vertical.withIndex().sumOf {
        it.value.reduce(operators[it.index])
    }
}
