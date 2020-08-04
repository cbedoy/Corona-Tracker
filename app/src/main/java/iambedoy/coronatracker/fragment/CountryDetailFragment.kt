package iambedoy.coronatracker.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import iambedoy.coronatracker.R
import iambedoy.coronatracker.fragment.CountryDetailFragment.ChartType.*
import iambedoy.coronatracker.viewmodel.CountryDetailViewModel
import kotlinx.android.synthetic.main.details_view.*
import kotlinx.android.synthetic.main.fragment_country_detail.*
import org.koin.android.ext.android.inject


/**
 * Corona Tracker
 *
 * Created by bedoy on 25/03/20.
 */
class CountryDetailFragment : Fragment(){

    private val viewModel by inject<CountryDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country_detail, container, false)
    }

    enum class ChartType{
        Deaths, Recovered, Cases
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        details_container.visibility = View.VISIBLE

        chart_deaths.setNoDataText("Loading")
        chart_recovered.setNoDataText("Loading")
        chart_cases.setNoDataText("Loading")

        viewModel.historical.observe(viewLifecycleOwner, Observer { historical ->
            fillChart(historical.deaths, chart_deaths, Deaths)
            fillChart(historical.recovered, chart_recovered, Recovered)
            fillChart(historical.cases, chart_cases, Cases)
        })

        viewModel.global.observe(viewLifecycleOwner, Observer { global ->
            details_container.visibility = View.VISIBLE
            cases_count_view.text = "${global.cases}"
            today_cases_count_view.text = "${global.todayCases}"
            deaths_count_view.text = "${global.deaths}"
            today_deaths_count_view.text = "${global.todayDeaths}"
            recovered_count_view.text = "${global.recovered}"
            active_count_view.text = "${global.active}"
            critical_count_view.text = "${global.critical}"
        })
    }

    private fun fillChart(map: Map<String, Long>, chart: LineChart, type: ChartType) {
        val dataSource = map.entries.mapIndexed { index, entry ->
            Entry(index.toFloat(), entry.value.toFloat())
        }

        val textColor = when(type){
            Deaths -> ContextCompat.getColor(requireContext(), R.color.total_deaths_color)
            Recovered -> ContextCompat.getColor(requireContext(), R.color.recovered_color)
            Cases -> ContextCompat.getColor(requireContext(), R.color.total_cases_color)
        }
        val dataSet = LineDataSet(dataSource, type.toString()).apply {
            lineWidth = 0F
            circleRadius = 1F
            circleHoleRadius = 1f
            color = textColor
            circleColors = listOf(textColor)

            setDrawValues(false)
        }
        chart.xAxis.isEnabled = false
        chart.axisLeft.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.legend.textSize = 18F
        chart.legend.textColor = textColor
        chart.setDrawBorders(false)
        //chart.setDrawGridBackground(false)
        chart.data = LineData(dataSet)
        chart.animateXY(1500, 1500)
        chart.notifyDataSetChanged()
        chart.invalidate()
    }
}