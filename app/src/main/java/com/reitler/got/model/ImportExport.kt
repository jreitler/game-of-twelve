package com.reitler.got.model

import androidx.core.util.Consumer
import androidx.core.util.Supplier
import com.reitler.got.model.data.access.MatchDatabase
import com.reitler.got.model.data.entity.MatchEntity
import com.reitler.got.model.data.entity.ScoreDataEntity
import com.reitler.got.model.match.MatchDataManager
import com.reitler.got.model.match.Player
import com.reitler.got.model.match.PlayerManager
import java.util.*

/**
 * Utility for exporting and importing game data
 */
class ImportExport(private val database: MatchDatabase) {

    private val matchDataManager: MatchDataManager = MatchDataManager(database)
    private val playerManager: PlayerManager = PlayerManager(database.playerDao)

    companion object Constants {
        private const val HEADER_PLAYER = "player_id;player_name"
        private const val HEADER_MATCH = "match_id;end_date;start_date;active_player"
        private const val HEADER_SCORE =
            "score_id;fk_player_id;fk_match_id;player_order;score1;score2;score3;score4;score5;score6;score7;score8;score9;score10;score11;score12"

        private val PATTERN_MATCH = Regex("\\d+;\\d+;\\d+;\\d+")
        private val PATTERN_PLAYER = Regex("\\d+;\\w+")
        private val PATTERN_SCORE = Regex("\\d+;\\d+;\\d+;\\d+;\\d;\\d;\\d;\\d;\\d;\\d;\\d;\\d;\\d;\\d;\\d;\\d")
    }

    /**
     * Export data as CSV to the given consumer
     */
    fun exportData(consumer: Consumer<String>) {

        // export player
        consumer.accept(HEADER_PLAYER)
        database.playerDao.allPlayers.forEach { player -> consumer.accept("${player.playerId};${player.name}") }

        // export matches
        consumer.accept(HEADER_MATCH)
        database.matchDao.allMatches.filter { match -> match.endDate != null }
            .forEach { m -> consumer.accept(formatMatch(m)) }

        // export scoreData
        consumer.accept(HEADER_SCORE)
        database.scoreDao.allScores.filterNotNull()
            .forEach { score -> consumer.accept(formatScore(score)) }
    }

    private fun formatScore(score: ScoreDataEntity): String {
        return "${score.id};${score.playerId};${score.matchId};" + "${score.order};" +
                "${score.score1};${score.score2};${score.score3};${score.score4};${score.score5};${score.score6};" +
                "${score.score7};${score.score8};${score.score9};${score.score10};${score.score11};${score.score12}"
    }

    private fun formatMatch(match: MatchEntity): String {
        return "${match.matchId};${match.endDate.time};${match.startDate.time};${match.activePlayer}"
    }

    /**
     * Import data by reading CSV from given supplier
     */
    fun import(stream: Supplier<String?>) {
        var mode = ParseMode.UNKNOWN
        var data = stream.get()
        val importModel = ImportModel()
        while (data != null) {
            if (HEADER_MATCH == data) {
                mode = ParseMode.MATCH
            } else if (HEADER_PLAYER == data) {
                mode = ParseMode.PLAYER
            } else if (HEADER_SCORE == data) {
                mode = ParseMode.SCORE
            } else {
                when (mode) {
                    ParseMode.PLAYER -> parsePlayer(data, importModel)
                    ParseMode.MATCH -> parseMatch(data, importModel)
                    ParseMode.SCORE -> parseScore(data, importModel)
                    ParseMode.UNKNOWN -> mode = parseMode(data)
                }
            }
            data = stream.get()
        }

    }

    private fun parseMode(data: String): ParseMode {
        return when (data) {
            HEADER_PLAYER -> ParseMode.PLAYER
            HEADER_MATCH -> ParseMode.MATCH
            HEADER_SCORE -> ParseMode.SCORE
            else -> ParseMode.UNKNOWN
        }
    }

    private fun parseScore(data: String, importModel: ImportModel) {
        if(!PATTERN_SCORE.matches(data)){
            return
        }

        val split = data.split(';')
        val matchEntity = importModel.matches[split[2].toInt()] ?: error("Inconsistent State")
        val player = importModel.players[split[1].toInt()] ?: error("Inconsistent State")
        val score =
            matchDataManager.createScoreDataEntity(matchEntity.matchId, player.id, split[3].toInt())

        score.score1 = split[4].toInt()
        score.score2 = split[5].toInt()
        score.score3 = split[6].toInt()
        score.score4 = split[7].toInt()
        score.score5 = split[8].toInt()
        score.score6 = split[9].toInt()
        score.score7 = split[10].toInt()
        score.score8 = split[11].toInt()
        score.score9 = split[12].toInt()
        score.score10 = split[13].toInt()
        score.score11 = split[14].toInt()
        score.score12 = split[15].toInt()

        matchDataManager.save(score)
    }

    private fun parseMatch(data: String, importModel: ImportModel) {
        if(!PATTERN_MATCH.matches(data)){
            return
        }
        val split = data.split(';')
        importModel.matches[split[0].toInt()] = matchDataManager.createMatchEntity().also {
            it.endDate = Date(split[1].toLong())
            it.startDate = Date(split[2].toLong())
            it.activePlayer = split[3].toInt()
            matchDataManager.save(it)
        }
    }

    private fun parsePlayer(data: String, importModel: ImportModel) {
        if(!PATTERN_PLAYER.matches(data)){
            return
        }
        val split = data.split(';')
        importModel.players[split[0].toInt()] = playerManager.getOrCreatePlayerForName(split[1])
    }

    private class ImportModel {

        val players = LinkedHashMap<Int, Player>()
        val matches = LinkedHashMap<Int, MatchEntity>()
    }

    private enum class ParseMode {
        PLAYER,
        MATCH,
        SCORE,
        UNKNOWN;
    }
}