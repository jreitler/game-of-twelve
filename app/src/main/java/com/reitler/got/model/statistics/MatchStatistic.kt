package com.reitler.got.model.statistics

import java.util.*
import java.util.stream.Collectors

class MatchStatistic {
    private val scores: MutableList<Int> = ArrayList()

    fun add(score: Int) {
        scores.add(score)
    }

    val scoreSum: Int
        get() = scores.sum()
    val playerCount: Int
        get() = scores.size
    val avgPlayerScore: Double
        get() = scoreSum.toDouble() / playerCount
    val maxScore: Int
        get() = scores.maxOf { it }
}