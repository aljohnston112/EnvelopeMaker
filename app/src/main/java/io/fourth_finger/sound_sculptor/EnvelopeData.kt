package io.fourth_finger.sound_sculptor

class EnvelopeData(
    val envelopeArray: FloatArray,
    val function: String,
    val column: Int,
    val start: Double,
    val end: Double,
    val length: Double,
    val minY: Double,
    val maxY: Double,
    val cycles: Double
)