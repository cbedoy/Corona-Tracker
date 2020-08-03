package iambedoy.coronatracker.fragment.countries.items

import android.view.View
import android.widget.TextView
import coil.api.load
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import iambedoy.coronatracker.R
import iambedoy.coronatracker.dto.Country
import iambedoy.coronatracker.dto.Global
import iambedoy.formattedCountry
import iambedoy.setCollapseExpand
import kotlinx.android.synthetic.main.country_cell.*
import kotlinx.android.synthetic.main.details_view.*

class CountryItem(private val country: Country) : Item(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.country_name_view.text = country.formattedCountry(viewHolder.itemView)
        viewHolder.country_name_view.setCollapseExpand(viewHolder.details_container, {

        }, {

        })
        viewHolder.country_flag_view.load(country.countryInfo?.flag){
            crossfade(true)

        }
        viewHolder.cases_count_view.text = "${country.cases}"
        viewHolder.today_cases_count_view.text = "${country.todayCases}"
        viewHolder.deaths_count_view.text = "${country.deaths}"
        viewHolder.today_deaths_count_view.text = "${country.todayDeaths}"
        viewHolder.recovered_count_view.text = "${country.recovered}"
        viewHolder.active_count_view.text = "${country.active}"
        viewHolder.critical_count_view.text = "${country.critical}"
    }

    override fun getLayout(): Int {
        return R.layout.country_cell
    }
}

class GlobalItem(private val global: Global) : Item(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.details_container.visibility = View.VISIBLE
        viewHolder.cases_count_view.text = "${global.cases}"
        viewHolder.today_cases_count_view.text = "${global.todayCases}"
        viewHolder.deaths_count_view.text = "${global.deaths}"
        viewHolder.today_deaths_count_view.text = "${global.todayDeaths}"
        viewHolder.recovered_count_view.text = "${global.recovered}"
        viewHolder.active_count_view.text = "${global.active}"
        viewHolder.critical_count_view.text = "${global.critical}"
    }

    override fun getLayout(): Int {
        return R.layout.global_cell
    }

}