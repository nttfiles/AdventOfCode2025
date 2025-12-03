import utils.resource

fun main() {
    val lines = resource("Day3.txt").lines()
    Day3(lines).printResults()
}

class Day3(lines: List<String>) : Day() {
    val banks: List<List<Int>> = lines.map { l ->
        l.toCharArray().map { it.digitToIntOrNull() ?: 0 }
    }

    override fun part1() = banks.sumOf { bank ->
        val first = bank.slice(0..<bank.lastIndex).max()
        val second = bank.slice(bank.indexOf(first) + 1..bank.lastIndex).max()

        10 * first + second
    }

    override fun part2() = banks.sumOf { bank ->
        var remaining = bank

        (0..11).reversed().fold(0L) { res, idx ->
            val next = remaining.slice(0..remaining.lastIndex - idx).max()
            remaining = remaining.slice(remaining.indexOf(next) + 1..remaining.lastIndex)

            res * 10 + next
        }
    }
}
