package com.reitler.got.view.statistics

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TableLayout
import androidx.lifecycle.ViewModelProvider
import com.reitler.got.R
import com.reitler.got.databinding.ActivityStatisticsBinding
import com.reitler.got.databinding.ViewStatisticsEntryBinding
import com.reitler.got.databinding.ViewStatisticsHeaderBinding
import com.reitler.got.model.statistics.PlayerStatistic
import com.reitler.got.view.BaseActivity
import com.reitler.got.vm.StatisticsViewModel
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class StatisticsActivity : BaseActivity() {
    private val REQUEST_CODE_CREATE_FILE = 1
    private val REQUEST_CODE_READ_FILE = 2
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

        viewModel.startDate.observe(this) { binding.statisticsDateStart.setText(if (null != it) dateFormat.format(it) else "") }
        viewModel.endDate.observe( this,)   { binding.statisticsDateEnd.setText(if (null != it) dateFormat.format(it) else "") }
        viewModel.playerStatistics.observe(this) { setPlayerStatistics(it) }

        binding.statisticsDateStart.setOnClickListener {
            val initialDate = viewModel.startDate.value ?: viewModel.firstMatchStart.value!!
            pickDate(
                { d -> viewModel.setStart(d) }, initialDate, viewModel.firstMatchStart.value?.time
                    ?: Date().time, Date().time, false
            )
        }

        binding.statisticsDateEnd.setOnClickListener {
            val minDate = viewModel.startDate.value ?: viewModel.firstMatchStart.value
            val initialDate = viewModel.endDate.value ?: Date()
            pickDate(
                { d -> viewModel.setEnd(d) }, initialDate, minDate?.time
                    ?: Date().time, Date().time, true
            )
        }

        binding.statisticExport.setOnClickListener {
            triggerExport()
        }
        binding.statisticImport.setOnClickListener{
            triggerImport()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_CREATE_FILE && resultCode == RESULT_OK) {
            data?.data?.also { documentUri ->
                contentResolver.takePersistableUriPermission(
                    documentUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                val pfd = contentResolver.openFileDescriptor(documentUri, "w")
                viewModel.export(pfd)
            }
        }
        else if (requestCode == REQUEST_CODE_READ_FILE && resultCode == RESULT_OK) {
            data?.data?.also { documentUri ->
                contentResolver.takePersistableUriPermission(
                    documentUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                val pfd = contentResolver.openFileDescriptor(documentUri, "r")
                viewModel.import(pfd)
            }
        }
    }

    private fun triggerImport() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TITLE, resources.getString(R.string.statistics_default_file_name))

        startActivityForResult(intent, REQUEST_CODE_READ_FILE)

    }


    private fun triggerExport() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TITLE, resources.getString(R.string.statistics_default_file_name))
        
        startActivityForResult(intent, REQUEST_CODE_CREATE_FILE)

    }

    private fun pickDate(
        callback: DatePickedCallback,
        initialDate: Date,
        minDate: Long,
        maxDate: Long,
        endOfDay: Boolean
    ) {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val c = Calendar.getInstance()
                if (endOfDay) {
                    c.set(year, month, dayOfMonth, 23, 59)
                } else {
                    c.set(year, month, dayOfMonth, 0, 0)
                }
                callback.datePicked(c.time)
            }
        val c = Calendar.getInstance()
        c.time = initialDate
        val dialog = DatePickerDialog(
            this,
            R.style.GotDatePickerDialogTheme,
            dateSetListener,
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        )
        dialog.datePicker.minDate = minDate
        dialog.datePicker.maxDate = maxDate
        dialog.setButton(
            DialogInterface.BUTTON_NEUTRAL,
            resources.getString(R.string.statistics_date_clear)
        ) { _, _ ->
            callback.datePicked(null)
        }
        dialog.show()
    }

    private fun setPlayerStatisticsHeader(table: TableLayout) {
        val header: ViewStatisticsHeaderBinding =
            ViewStatisticsHeaderBinding.inflate(layoutInflater, table, false)

        val padding = resources.getDimensionPixelSize(R.dimen.table_header_padding)
        header.root.setPadding(padding, padding, padding, padding)

        table.addView(header.root)
    }

    private fun setPlayerStatistics(playerStatistics: List<PlayerStatistic>) {
        val table = binding.statisticsPlayerTable

        // first, remove all table rows that might be present already
        val childCount: Int = table.childCount
        for (i in childCount - 1 downTo 1) {
            table.removeView(table.getChildAt(i))
        }

        for (p in playerStatistics) {

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

        entryBinding.statisticsEntryName.text =
            resources.getString(R.string.statistics_table_name_overall)
        entryBinding.statisticsEntryMatchCount.text = viewModel.matches.value?.count.toString()
        entryBinding.statisticsEntryWinCount.text = " - "
        entryBinding.statisticsEntryMaxRemains.text = viewModel.matches.value?.maxRemains.toString()
        entryBinding.statisticsEntryAvgRemains.text =
            "%.2f".format(viewModel.matches.value?.avgScoresPerPlayer)
        entryBinding.statisticsEntryTotalRemains.text =
            viewModel.matches.value?.totalRemains.toString()
        table.addView(entryBinding.root)
    }


    fun interface DatePickedCallback {
        fun datePicked(result: Date?)
    }
}