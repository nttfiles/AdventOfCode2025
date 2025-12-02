import utils.pow
import utils.resource
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.roundToInt

fun main() {
    val input = resource("Day2.txt")
    Day2(input).printResults()
}

class Day2(input: String) : Day() {
    val ranges: List<LongRange> = input.split(",").map { r ->
        val (from, to) = r.split("-").map { it.toLongOrNull() ?: 0 }

        from..to
    }

    override fun part1(): Long = ranges.sumOf { r ->
        r.sumOf { n ->
            floor(log10(n.toDouble()) + 1).roundToInt().let { digits ->
                val divisor = 10.pow(digits / 2) + 1
                if (digits % 2 == 0 && n % divisor == 0L) n else 0L
            }
        }
    }

    override fun part2(): Long = ranges.sumOf { r ->
        r.sumOf { n ->
            floor(log10(n.toDouble()) + 1).roundToInt().let { digits ->
                (1..digits / 2).any { period ->
                    digits % period == 0 && run {
                        val divisor = (0..<digits / period).sumOf {
                            10.pow(it * period)
                        }
                        n % divisor == 0L
                    }
                }.let { if (it) n else 0L }
            }
        }
    }
}
