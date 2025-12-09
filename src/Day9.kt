import utils.resource
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val lines = resource("Day9.txt").lines()
    Day9(lines).printResults()
}

class Day9(lines: List<String>) : Day() {
    val coordinates = lines.map { l ->
        val (x, y) = l.split(',').map { it.toLongOrNull() ?: 0 }
        Coordinate2d(x, y)
    }

    val rectangles = run {
        var remaining = coordinates

        coordinates.flatMap { c ->
            remaining = remaining.drop(1)

            remaining.map {
                Rect(c, it)
            }
        }
    }

    val lines = (coordinates + coordinates[0]).windowed(2).map { (p1, p2) ->
        if (p1.x == p2.x) {
            Vertical(p1, p2)
        } else {
            Horizontal(p1, p2)
        }
    }

    val verticals = this.lines.filterIsInstance<Vertical>().sortedBy { it.x }

    override fun part1() = rectangles.maxOf { it.area }

    override fun part2() = rectangles.sortedByDescending { it.area }.first { rect ->
        listOf(
            rect.start,
            Coordinate2d(rect.end.x, rect.start.y),
            rect.end,
            Coordinate2d(rect.start.x, rect.end.y),
            rect.start
        ).windowed(2).flatMap { (p1, p2) -> p1..p2 }.all { it.isContained() }
    }.area

    fun Coordinate2d.isContained() =
        verticals.takeWhile { (it.x < x && y in it.y) }.count() % 2 != 0 ||
            lines.any {
                when (it) {
                    is Horizontal -> y == it.y && x in it.x
                    is Vertical -> x == it.x && y in it.y
                }
            } ||
            this in coordinates
}

data class Coordinate2d(val x: Long, val y: Long)

operator fun Coordinate2d.rangeTo(other: Coordinate2d) =
    (min(x, other.x)..max(x, other.x)).flatMap { x ->
        (min(y, other.y)..max(y, other.y)).map { y ->
            Coordinate2d(x, y)
        }
    }

class Rect(p1: Coordinate2d, p2: Coordinate2d) {
    val start = Coordinate2d(min(p1.x, p2.x), min(p1.y, p2.y))
    val end = Coordinate2d(max(p1.x, p2.x), max(p1.y, p2.y))

    val area = (abs(start.x - end.x) + 1) * (abs(start.y - end.y) + 1)
}

sealed interface Line

class Vertical(p1: Coordinate2d, p2: Coordinate2d) : Line {
    val x = p1.x
    val y = min(p1.y, p2.y)..<max(p1.y, p2.y)
}

class Horizontal(p1: Coordinate2d, p2: Coordinate2d) : Line {
    val x = min(p1.x, p2.x)..<max(p1.x, p2.x)
    val y = p1.y
}
