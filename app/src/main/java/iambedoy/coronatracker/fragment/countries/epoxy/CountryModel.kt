package iambedoy.coronatracker.fragment.countries.epoxy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import iambedoy.KotlinModel
import iambedoy.coronatracker.R
import iambedoy.coronatracker.dto.Country
import iambedoy.coronatracker.dto.Global
import iambedoy.formattedCountry
import iambedoy.setCollapseExpand

/**
 * Corona Tracker
 *
 * Created by bedoy on 12/07/20.
 */
class CountryModel (
    private val country: Country
): KotlinModel(R.layout.country_cell){

    private val flagView  by bind<ImageView>(R.id.country_flag_view)
    private val detailsContainer by bind<View>(R.id.details_container)
    private val countryName  by bind<TextView>(R.id.country_name_view)
    private val cases by bind<TextView>(R.id.cases_count_view)
    private val todayCases by bind<TextView>(R.id.today_cases_count_view)
    private val deaths by bind<TextView>(R.id.deaths_count_view)
    private val todayDeaths by bind<TextView>(R.id.today_deaths_count_view)
    private val recovered by bind<TextView>(R.id.recovered_count_view)
    private val active by bind<TextView>(R.id.active_count_view)
    private val critical by bind<TextView>(R.id.critical_count_view)

    override fun bind() {
        countryName.text = country.formattedCountry(countryName)
        countryName.setCollapseExpand(detailsContainer, {

        }, {

        })
        flagView.load(country.countryInfo?.flag){
            crossfade(true)

        }
        cases.text = "${country.cases}"
        todayCases.text = "${country.todayCases}"
        deaths.text = "${country.deaths}"
        todayDeaths.text = "${country.todayCases}"
        recovered.text = "${country.recovered}"
        active.text = "${country.active}"
        critical.text = "${country.critical}"
    }
}