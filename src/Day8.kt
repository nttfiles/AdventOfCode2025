import utils.pow
import utils.sqrt
import utils.resource
import kotlin.math.abs

fun main() {
    val lines = resource("Day8.txt").lines()
    Day8(lines).printResults()
}

class Day8(lines: List<String>) : Day() {
    val coordinates = lines.map { l ->
        val (x, y, z) = l.split(',').map { it.toLongOrNull() ?: 0 }
        Coordinate3d(x, y, z)
    }

    val distances = run {
        var remaining = coordinates

        coordinates.flatMap { c ->
            remaining = remaining.drop(1)

            remaining.map {
                Pair(c to it, c.distanceTo(it))
            }
        }.sortedBy { it.second }
    }

    override fun part1() = distances.take(1000).map { it.first }.fold(listOf<Set<Coordinate3d>>()) { sets, pair ->
        sets.toMutableList().apply {
            filter { pair.first in it || pair.second in it }.takeIf { it.isNotEmpty() }?.let { existing ->
                removeAll(existing)
                add(existing.reduce { a, b -> a union b } + pair.toList())
            } ?: run {
                add(pair.toList().toSet())
            }
        }
    }.map { it.size }.sortedDescending().take(3).reduce { a, b -> a * b }

    override fun part2(): Long {
        val sets = coordinates.map(::setOf).toMutableList()

        return distances.map { it.first }.first { pair ->
            sets.apply {
                filter { pair.first in it || pair.second in it }.let { existing ->
                    removeAll(existing)
                    add(existing.reduce { a, b -> a union b } + pair.toList())
                }
            }

            sets.size == 1
        }.let { (first, second) -> first.x * second.x }
    }
}

data class Coordinate3d(val x: Long, val y: Long, val z: Long)

fun Coordinate3d.distanceTo(other: Coordinate3d) =
    sqrt(abs(x - other.x).pow(2) + abs(y - other.y).pow(2) + abs(z - other.z).pow(2))
