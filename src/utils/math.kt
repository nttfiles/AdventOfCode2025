package utils

import kotlin.math.pow
import kotlin.math.roundToInt

fun Int.pow(n: Int) = toFloat().pow(n).roundToInt()
