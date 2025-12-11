import utils.resource
import utils.words

fun main() {
    val records = resource("Day10.txt").lines().map { it.words() }
    Day10(records).printResults()
}

class Day10(records: List<List<String>>) : Day() {
    val diagrams = records.map { r ->
        val lights = r.first().parseToListOf<Boolean>('[' to ']')
        val schematics =
            r.drop(1).takeWhile { it.startsWith('(') }
                .map { it.parseToListOf<Int>('(' to ')').toSet() }
                .sortedByDescending { it.size }
        val joltage = r.last().parseToListOf<Int>('{' to '}')

        Diagram(lights, schematics, joltage)
    }

    override fun part1() = diagrams.sumOf { diagram ->
        val found = mutableSetOf<List<Boolean>>()
        var presses = 0
        var patterns = setOf(List(diagram.lights.size) { false })

        while (patterns.none { it == diagram.lights }) {
            presses++

            patterns = patterns.flatMap { p ->
                diagram.schematics.map { schematic ->
                    p.mapIndexed { idx, light ->
                        if (idx in schematic) !light else light
                    }
                }
            }.toSet() - found

            found.addAll(patterns)
        }

        presses
    }

    override fun part2() = diagrams.sumOf { diagram ->
        fun solve(
            multipliers: List<Int> = List(diagram.schematics.size) { 0 },
            remaining: List<Int> = diagram.joltages,
            index: Int = 0
        ): Pair<Boolean, Int> {
            val indices = diagram.schematics[index]
            val max = indices.minOf { remaining[it] }

            (max downTo 0).forEach { added ->
                if (index == multipliers.lastIndex) {
                    val solved = multipliers.toMutableList()
                        .apply { this[index] = added }
                        .foldIndexed(List(diagram.joltages.size) { 0 }) { idx, res, add ->
                            res.toMutableList().apply {
                                diagram.schematics[idx].forEach {
                                    this[it] += add
                                }
                            }
                        } == diagram.joltages

                    if (solved) return true to added
                } else {
                    val solution = solve(
                        multipliers.toMutableList().apply { this[index] = added },
                        remaining.toMutableList().apply {
                            indices.forEach { this[it] -= added }
                        },
                        index + 1
                    )

                    if (solution.first) return true to solution.second + added
                }
            }

            return false to 0
        }

        solve().second
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Any> String.parseToListOf(brackets: Pair<Char, Char>): List<T> =
        substringAfter(brackets.first).substringBefore(brackets.second).let { s ->
            when (T::class) {
                Boolean::class -> s.toCharArray().map { it == '#' }
                Int::class -> s.split(',').map {
                    it.toInt()
                }

                else -> error("Incompatible type")
            } as List<T>
        }
}

data class Diagram(val lights: List<Boolean>, val schematics: List<Set<Int>>, val joltages: List<Int>)
