package com.kbj.aqiindex.utils

import java.util.*
import kotlin.math.pow
import kotlin.math.roundToInt

fun Number.roundTo(
    numFractionDigits: Int
) = "%.${numFractionDigits}f".format(this, Locale.ENGLISH)