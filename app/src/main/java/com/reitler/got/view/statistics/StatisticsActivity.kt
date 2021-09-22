package com.reitler.got.view.statistics

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.reitler.got.R
import com.reitler.got.databinding.ActivityStatisticsBinding
import com.reitler.got.databinding.ViewStatisticsEntryBinding
import com.reitler.got.databinding.ViewStatisticsHeaderBinding
import com.reitler.got.model.statistics.MatchStatistics
import com.reitler.got.model.statistics.PlayerStatistic
import com.reitler.got.vm.StatisticsViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class StatisticsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var viewModel: StatisticsViewModel
    private lateinit var dateFormat: DateFormat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dateFormat = SimpleDateFormat(resources.getString(R.string.statistics_date_format))
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setPlayerStatisticsHeader(binding.statisticsPlayerTable)

        viewModel = ViewModelProvider(this).get(StatisticsViewModel::class.java)

        viewModel.startDate.observe(this, { binding.statisticsDateStart.setText(if (null != it) dateFormat.format(it) else "") })
        viewModel.endDate.observe(this, { binding.statisticsDateEnd.setText(if (null != it) dateFormat.format(it) else "") })
        viewModel.playerStatistics.observe(this, { setPlayerStatistics(it) })

        binding.statisticsDateStart.setOnClickListener {
            val initialDate = viewModel.startDate.value ?: viewModel.firstMatchStart.value!!
            pickDate({ d -> viewModel.setStart(d) }, initialDate, viewModel.firstMatchStart.value?.time
                    ?: Date().time, Date().time, false)
        }

        binding.statisticsDateEnd.setOnClickListener {
            val minDate = viewModel.startDate.value ?: viewModel.firstMatchStart.value
            val initialDate = viewModel.endDate.value ?: Date()
            pickDate({ d -> viewModel.setEnd(d) }, initialDate, minDate?.time
                    ?: Date().time, Date().time, true)
        }
    }

    private fun pickDate(callback: DatePickedCallback, initialDate: Date, minDate: Long, maxDate: Long, endOfDay: Boolean) {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val c = Calendar.getInstance()
            if(endOfDay){
                c.set(year, month, dayOfMonth, 23, 59)
            }
            else {
                c.set(year, month, dayOfMonth, 0, 0)
            }
            callback.datePicked(c.time)
        }
        val c = Calendar.getInstance()
        c.time = initialDate
        val dialog = DatePickerDialog(this, R.style.GotDatePickerDialogTheme, dateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
        dialog.datePicker.minDate = minDate
        dialog.datePicker.maxDate = maxDate
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, resources.getString(R.string.statistics_date_clear)) { _, _ ->
            callback.datePicked(null)
        }
        dialog.show()
    }

    private fun setPlayerStatisticsHeader(table: TableLayout){
        val header: ViewStatisticsHeaderBinding = ViewStatisticsHeaderBinding.inflate(layoutInflater, table, false)

        val padding = resources.getDimensionPixelSize(R.dimen.table_header_padding)
        header.root.setPadding(padding, padding, padding, padding)

        table.addView(header.root)
    }

    private fun setPlayerStatistics(playerStatistics: List<PlayerStatistic>){
        val table = binding.statisticsPlayerTable

        // first, remove all table rows that might be present already
        val childCount: Int = table.childCount
        for (i in childCount - 1 downTo 1) {
            table.removeView(table.getChildAt(i))
        }

        for (p in playerStatistics){

            val entryBinding = ViewStatisticsEntryBinding.inflate(layoutInflater, table, false)

            entryBinding.statisticsEntryName.text = p.player.name
            entryBinding.statisticsEntryMatchCount.text = p.matchCount.toString()
            entryBinding.statisticsEntryWinCount.text = p.winCount.toString()
            entryBinding.statisticsEntryMaxRemains.text = p.highestRemaining.toString()
            entryBinding.statisticsEntryAvgRemains.text = "%.2f".format(p.averageRemaining)
            entryBinding.statisticsEntryTotalRemains.text = p.totalRemaining.toString()

            table.addView(entryBinding.root)
        }

        val entryBinding = ViewStatisticsEntryBinding.inflate(layoutInflater, table, false)

        entryBinding.statisticsEntryName.text = resources.getString(R.string.statistics_table_name_overall)
        entryBinding.statisticsEntryMatchCount.text = viewModel.matches.value?.count.toString()
        entryBinding.statisticsEntryWinCount.text = " - "
        entryBinding.statisticsEntryMaxRemains.text = viewModel.matches.value?.maxRemains.toString()
        entryBinding.statisticsEntryAvgRemains.text = "%.2f".format(viewModel.matches.value?.avgScoresPerPlayer)
        entryBinding.statisticsEntryTotalRemains.text = viewModel.matches.value?.totalRemains.toString()
        table.addView(entryBinding.root)
    }


    fun interface DatePickedCallback {
        fun datePicked(result: Date?)
    }
}