import utils.Matrix
import utils.indexed
import utils.get
import utils.set
import utils.resource

fun main() {
    val grid = resource("Day4.txt").lines().map { it.toList() }
    Day4(grid).printResults()
}

class Day4(val grid: Matrix<Char>) : Day() {
    override fun part1() = grid.indexed().sumOf { row ->
        row.count { (idx, cell) ->
            cell == '@' && run {
                val rx = (idx.first - 1).coerceIn(0, grid.lastIndex)..(idx.first + 1).coerceIn(0, grid.lastIndex)
                val ry = (idx.second - 1).coerceIn(0, row.lastIndex)..(idx.second + 1).coerceIn(0, row.lastIndex)

                grid[rx, ry].flatMap { it }.count { it == '@' } <= 4
            }
        }
    }

    override fun part2(): Int {
        var remaining = grid
        val rem = remaining.mapTo(mutableListOf()) { it.toMutableList() }

        var total = 0
        var count = -1

        while (count != 0) {
            count = remaining.indexed().sumOf { row ->
                row.count { (idx, cell) ->
                    cell == '@' && run {
                        val rx = (idx.first - 1).coerceIn(0, remaining.lastIndex)..(idx.first + 1).coerceIn(0, remaining.lastIndex)
                        val ry = (idx.second - 1).coerceIn(0, row.lastIndex)..(idx.second + 1).coerceIn(0, row.lastIndex)

                        (remaining[rx, ry].flatMap { it }.count { it == '@' } <= 4).also {
                            if (it) rem[idx] = 'x'
                        }
                    }
                }
            }

            total += count
            remaining = rem
        }

        return total
    }
}
