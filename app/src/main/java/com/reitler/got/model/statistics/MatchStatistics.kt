package com.reitler.got.model.statistics

import java.util.*
import java.util.stream.Collectors

class MatchStatistics {
    private val matches: MutableList<MatchStatistic> = ArrayList()
    val count: Int
        get() = matches.size
    val avgScoresPerMatch: Double
        get() {
            if (count == 0) {
                return 0.0
            }
            val absoluteScores = matches.stream().collect(Collectors.summingDouble { m -> m.scoreSum.toDouble() })
            return absoluteScores / count
        }
    val avgScoresPerPlayer: Double
        get() {
            if (count == 0) {
                return 0.0
            }
            val sumAvgScores = matches.stream().collect(Collectors.summingDouble(MatchStatistic::avgPlayerScore))
            return (sumAvgScores
                    / count)
        }
    val totalRemains: Int
        get() = if(matches.isEmpty()) 0 else matches.sumBy { it.scoreSum }
    val maxRemains: Int
        get() = if(matches.isEmpty()) 0 else matches.maxOf { it.maxScore }

    fun addMatch(match: MatchStatistic) {
        matches.add(match)
    }
}