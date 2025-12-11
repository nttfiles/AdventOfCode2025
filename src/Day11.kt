import utils.resource

fun main() {
    val lines = resource("Day11.txt").lines()
    Day11(lines).printResults()
}

class Day11(lines: List<String>) : Day() {
    val devices = lines.associate {
        it.substringBefore(":") to it.substringAfter(":").trim().split(" ").toMutableList()
    }

    override fun part1(): Int {
        var paths = 0

        var positions = listOf("you")

        while (positions.isNotEmpty()) {
            positions = positions.flatMap { devices[it] ?: listOf() }.mapNotNull {
                if (it == "out") {
                    paths++
                    null
                } else {
                    it
                }
            }
        }

        return paths
    }

    override fun part2(): Long {
        var paths = 0L

        var positions = listOf(Triple("svr", 1L, 0))

        while (positions.isNotEmpty()) {
            positions = positions
                .flatMap { pos -> devices[pos.first]?.map { Triple(it, pos.second, pos.third) } ?: listOf() }
                .groupBy { it.first to it.third }.map { Triple(it.key.first,it.value.sumOf { p -> p.second }, it.key.second) }
                .mapNotNull {
                    when (it.first) {
                        "out" -> {
                            if (it.third == 2) paths += it.second
                            null
                        }
                        "dac", "fft" -> {
                            it.run { Triple(first, second, third + 1) }
                        }
                        else -> it
                    }
                }
        }

        return paths
    }
}
