package iambedoy.coronatracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import iambedoy.coronatracker.R
import iambedoy.coronatracker.models.Country
import iambedoy.coronatracker.models.Global
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.country_cell.*
import kotlinx.android.synthetic.main.country_cell.corona_cell_active_text
import kotlinx.android.synthetic.main.country_cell.corona_cell_cases_text
import kotlinx.android.synthetic.main.country_cell.corona_cell_deaths_text
import kotlinx.android.synthetic.main.country_cell.corona_cell_recovered_text
import kotlinx.android.synthetic.main.global_cell.*
import java.util.*

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaAdapter : RecyclerView.Adapter<BaseCoronaHolder>(){

    var dataModel = listOf<Any>()

    private val countryType = 0
    private val globalType = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseCoronaHolder {
        if (viewType == countryType){
            return CountryHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.country_cell,
                    parent,
                    false
                )
            )
        }else{
            return GlobalHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.global_cell,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return dataModel.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) globalType else countryType
    }

    override fun onBindViewHolder(holder: BaseCoronaHolder, position: Int) {
        val item = dataModel[position]
        holder.reloadItem(item)
    }


}

class CountryHolder(override val containerView: View): BaseCoronaHolder(containerView) {
    override fun reloadItem(item: Any) {
        item as Country

        corona_cell_country_name.text = item.country

        corona_cell_cases_per_one_million_text.text = "${item.casesPerOneMillion}"

        corona_cell_cases_text.text = "${item.cases}"

        corona_cell_today_cases_text.text = "${item.todayCases}"

        corona_cell_today_deaths_text.text = "${item.todayDeaths}"

        corona_cell_critical_text.text = "${item.critical}"

        corona_cell_recovered_text.text = "${item.recovered}"

        corona_cell_deaths_text.text = "${item.deaths}"

        corona_cell_active_text.text = "${item.active}"
    }
}

class GlobalHolder(override val containerView: View): BaseCoronaHolder(containerView){
    override fun reloadItem(item: Any) {
        item as Global

        global_cell_cases_text.text = "${item.cases}"

        global_cell_recovered_text.text = "${item.recovered}"

        global_cell_deaths_text.text = "${item.deaths}"

        global_cell_last_update.text = ""
    }
}

abstract class BaseCoronaHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{
    abstract fun reloadItem(item: Any)
}