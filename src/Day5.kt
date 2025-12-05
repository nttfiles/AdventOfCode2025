import utils.resource
import utils.splitByWhitespace

fun main() {
    val input = resource("Day5.txt")
    Day5(input).printResults()
}

class Day5(input: String) : Day() {
    val ranges: List<LongRange>
    val ingredients: List<Long>

    init {
        val (p1, p2) = input.splitByWhitespace()

        ranges = p1.lines().map { range ->
            val (start, end) = range.split("-").map { it.toLongOrNull() ?: 0 }
            start..end
        }.fold(listOf<LongRange>()) { res, r ->
            res.toMutableList().apply {
                val others = filter { r.first in it || it.first in r }

                if (others.any()) {
                    removeAll(others)

                    val start = listOf(r, *others.toTypedArray()).minOf { it.first }
                    val end = listOf(r, *others.toTypedArray()).maxOf { it.last }

                    add(start..end)
                } else {
                    add(r)
                }
            }
        }

        ingredients = p2.lines().map { it.toLongOrNull() ?: 0 }
    }

    override fun part1() = ingredients.count {
        ranges.any { r -> it in r }
    }

    override fun part2() = ranges.sumOf { it.last - it.first + 1 }
}
