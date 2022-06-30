package com.reitler.got.model.statistics

import com.reitler.got.model.ScoreDataUtil
import com.reitler.got.model.data.access.MatchDatabase
import com.reitler.got.model.data.entity.MatchEntity
import com.reitler.got.model.data.entity.PlayerEntity
import com.reitler.got.model.data.entity.ScoreDataEntity
import java.util.*

class StatisticsDataManager(private val database: MatchDatabase) {

    fun loadAllPlayerStatistics(from: Date?, to: Date?) : List<PlayerStatistic>{
        val fromDate = from ?: Date(0)
        val toDate = to ?: Date()
        val allPlayers = database.playerDao.allPlayers
        return allPlayers.map {  loadPlayerStatistic(it, fromDate, toDate) }.sortedBy { it.player.name }.filter { it.matchCount > 0 }
    }

    private fun loadPlayerStatistic(entity: PlayerEntity, from: Date, to: Date): PlayerStatistic {
        val result = PlayerStatistic(entity)
                val scores = database.scoreDao.getScoresForPlayer(entity.playerId, from, to)
        scores.forEach { s: ScoreDataEntity? -> result.addScore(ScoreDataUtil.getRemainingScore(s)) }
        return result
    }

    fun loadMatchStatistics(from: Date?, to: Date?): MatchStatistics {
        val result = MatchStatistics()
        val fromDate = from ?: Date(0)
        val toDate = to ?: Date()
        val allMatches = database.matchDao.getMatches(fromDate, toDate)
        allMatches.forEach { m: MatchEntity -> result.addMatch(getMatchStatistic(m)) }
        return result
    }

    fun loadFirstMatchDate(): Date {
        return database.matchDao.firstMatchStart ?: Date()
    }

    private fun getMatchStatistic(entity: MatchEntity): MatchStatistic {
        val result = MatchStatistic()
        val scoresForMatch = database.scoreDao.getScoresForMatch(entity.matchId)
        scoresForMatch.forEach { s: ScoreDataEntity? -> result.add(ScoreDataUtil.getRemainingScore(s)) }
        return result
    }
}