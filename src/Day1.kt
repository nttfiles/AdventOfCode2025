import utils.resource

fun main() {
    val lines = resource("Day1.txt").lines()
    Day1(lines).printResults()
}

class Day1(lines: List<String>) : Day() {
    val rotations = lines.map {
        var distance = it.substring(1).toIntOrNull() ?: 0
        if (it.first() == 'L') distance *= -1

        distance
    }

    override fun part1(): Int {
        var rotation = 50

        return rotations.fold(0) { count, rot ->
            rotation += rot
            if (rotation % 100 == 0) count + 1 else count
        }
    }

    override fun part2(): Int {
        var rotation = 50

        return rotations.fold(0) { count, rot ->
            val passes = if (rot >= 0) {
                (rot + rotation) / 100
            } else {
                (-rot + (100 - rotation) % 100) / 100
            }

            rotation = (rotation + rot % 100).mod(100)

            count + passes
        }
    }
}
