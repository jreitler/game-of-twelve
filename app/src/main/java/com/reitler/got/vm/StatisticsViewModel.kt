package com.reitler.got.vm

import android.app.Application
import android.os.ParcelFileDescriptor
import androidx.core.util.Supplier
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.reitler.got.GotApplication
import com.reitler.got.model.ImportExport
import com.reitler.got.model.match.MatchDataManager
import com.reitler.got.model.statistics.MatchStatistics
import com.reitler.got.model.statistics.PlayerStatistic
import com.reitler.got.model.statistics.StatisticsDataManager
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.concurrent.ExecutorService

class StatisticsViewModel(application: Application) : AndroidViewModel(application) {
    private val executor: ExecutorService
    private val dataManager: StatisticsDataManager
    private val importExport: ImportExport
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
        importExport = ImportExport(matchDataBase)
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


    fun export(pfd: ParcelFileDescriptor?) {
        executor.execute {
            try {
                pfd?.also {
                    val writer = FileOutputStream(it.fileDescriptor).bufferedWriter(Charsets.UTF_8)
                    writer.use { w ->
                        importExport.exportData { data ->
                            w.write(data)
                            w.newLine()
                        }
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace();
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
    }

    fun import(pfd: ParcelFileDescriptor?){
        executor.execute{
            try {
                pfd?.also {
                    val reader = FileInputStream(it.fileDescriptor).bufferedReader(Charsets.UTF_8)
                    reader.use { r ->
                        importExport.import{  r.readLine()?.trim() }
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace();
            } catch (e: IOException) {
                e.printStackTrace();
            }
            reloadStatistics(startDate.value, endDate.value)
        }
    }

}