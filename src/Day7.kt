import utils.resource

fun main() {
    val lines = resource("Day7.txt").lines()
    Day7(lines).printResults()
}

class Day7(val lines: List<String>) : Day() {
    val start = lines.first().indexOf('S').takeIf { it != -1 } ?: error("No starting point found")

    override fun part1(): Int {
        var beams = listOf(start)

        return lines.fold(0) { res, row ->
            var splits = 0

            beams = beams.flatMap {
                if (row[it] == '^') {
                    splits++
                    listOf(it - 1, it + 1)
                } else {
                    listOf(it)
                }
            }.distinct()

            res + splits
        }
    }

    override fun part2(): Long {
        var beams = listOf(Pair(1L, start))

        lines.forEach { row ->
            beams = beams.flatMap {
                if (row[it.second] == '^') {
                    listOf(Pair(it.first, it.second - 1), Pair(it.first, it.second + 1))
                } else {
                    listOf(it)
                }
            }.groupBy { it.second }.map { g ->
                g.value.sumOf { it.first } to g.key
            }
        }

        return beams.sumOf { it.first }
    }
}
