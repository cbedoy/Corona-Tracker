package iambedoy.coronatracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.corona_cell.*

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaAdapter : RecyclerView.Adapter<CoronaHolder>(){

    var dataModel = listOf<Corona>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoronaHolder {
        return CoronaHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.corona_cell, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataModel.size
    }

    override fun onBindViewHolder(holder: CoronaHolder, position: Int) {
        holder.reloadCorona(dataModel[position])
    }

}


class CoronaHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun reloadCorona(corona: Corona){
        corona_cell_country_name.text = corona.country

        corona_cell_cases_per_one_million_text.text = "${corona.casesPerOneMillion}"

        corona_cell_cases_text.text = "${corona.cases}"

        corona_cell_today_cases_text.text = "${corona.todayCases}"

        corona_cell_today_deaths_text.text = "${corona.todayDeaths}"

        corona_cell_critical_text.text = "${corona.critical}"

        corona_cell_recovered_text.text = "${corona.recovered}"

        corona_cell_deaths_text.text = "${corona.deaths}"

        corona_cell_today_cases_text.text = "${corona.totalCases}"
    }
}