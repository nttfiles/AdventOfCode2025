package utils

import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.math.sqrt

fun Int.pow(n: Int) = toFloat().pow(n).roundToInt()
fun Long.pow(n: Int) = toFloat().pow(n).roundToLong()

fun sqrt(x: Int) = sqrt(x.toFloat())
fun sqrt(x: Long) = sqrt(x.toDouble())
