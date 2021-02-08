package com.reitler.got.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.reitler.got.GotApplication
import com.reitler.got.model.statistics.MatchStatistics
import com.reitler.got.model.statistics.PlayerStatistic
import com.reitler.got.model.statistics.StatisticsDataManager
import java.util.*
import java.util.concurrent.ExecutorService

class StatisticsViewModel(application: Application) : AndroidViewModel(application) {
    private val executor: ExecutorService
    private val dataManager: StatisticsDataManager
    val matches = MutableLiveData<MatchStatistics>()
    val firstMatchStart = MutableLiveData<Date>()
    val startDate = MutableLiveData<Date>()
    val endDate = MutableLiveData<Date>()
    val playerStatistics = MutableLiveData<List<PlayerStatistic>>()

    init {
        val matchDataBase = (application as GotApplication).matchDataBase
        dataManager = StatisticsDataManager(matchDataBase)
        executor = application.executorService
        executor.execute {
            firstMatchStart.postValue(dataManager.loadFirstMatchDate())
            startDate.postValue(null)
            endDate.postValue(null)
            reloadStatistics(null, null)
        }
    }

    private fun reloadStatistics(start: Date?, end: Date?) {
            matches.postValue(dataManager.loadMatchStatistics(start, end))
            playerStatistics.postValue(dataManager.loadAllPlayerStatistics(start, end))
    }

    fun setStart(date: Date?) {
        startDate.postValue(date)
        executor.execute {
            reloadStatistics(date, endDate.value)
        }
    }

    fun setEnd(date: Date?) {
        endDate.postValue(date)
        executor.execute {
            reloadStatistics(startDate.value, date)
        }
    }

}