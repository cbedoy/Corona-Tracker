package iambedoy.coronatracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import iambedoy.coronatracker.R
import iambedoy.coronatracker.dto.Country
import iambedoy.coronatracker.setCollapseExpand
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.country_small_cell.*

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaAdapter : RecyclerView.Adapter<CoronaAdapter.CountryHolder>(){

    var dataModel = mutableListOf<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        return CountryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.country_small_cell,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataModel.size
    }


    inner class CountryHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun reload(country: Country) {

            corona_small_cell_country_name.text = "${country.country}"
            corona_small_cell_country_flag.load(country.countryInfo?.flag)
            corona_cell_cases_per_one_million_text.text = "${country.casesPerOneMillion}"
            corona_cell_cases_text.text = "${country.cases}"
            corona_cell_today_cases_text.text = "${country.todayCases}"
            corona_cell_today_deaths_text.text = "${country.todayDeaths}"
            corona_cell_critical_text.text = "${country.critical}"
            corona_cell_recovered_text.text = "${country.recovered}"
            corona_cell_deaths_text.text = "${country.deaths}"
            corona_cell_active_text.text = "${country.active}"
            corona_small_cell_country_name.setCollapseExpand(corona_small_cell_info_container, {

            },{

            })

            setIsRecyclable(false)
        }

    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.reload(dataModel[position])
    }
}

