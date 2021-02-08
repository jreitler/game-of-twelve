package com.reitler.got.model.statistics

import com.reitler.got.model.data.entity.PlayerEntity
import java.util.*
import java.util.stream.Collectors

class PlayerStatistic(val player: PlayerEntity) {
    private val scores: MutableList<Int> = LinkedList()
    fun addScore(remainingScore: Int) {
        scores.add(remainingScore)
    }

    val matchCount: Int
        get() = scores.size
    val winCount: Int
        get() = scores.count { it == 0 }
    val highestRemaining: Int
        get() = if (matchCount == 0) 0 else scores.maxOf { it }
    val averageRemaining: Double
        get() = if (matchCount == 0) 0.0 else scores.sum().toDouble() / (matchCount)
    val totalRemaining: Int
        get() = scores.sum()
}