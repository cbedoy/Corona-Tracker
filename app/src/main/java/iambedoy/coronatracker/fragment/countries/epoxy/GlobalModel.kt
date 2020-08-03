package iambedoy.coronatracker.fragment.countries.epoxy

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import iambedoy.KotlinModel
import iambedoy.coronatracker.R
import iambedoy.coronatracker.dto.Global

/**
 * Corona Tracker
 *
 * Created by bedoy on 12/07/20.
 */
class GlobalModel (
    private val global: Global
): KotlinModel(R.layout.global_cell){

    private val detailsContainer by bind<View>(R.id.details_container)
    private val cases by bind<TextView>(R.id.cases_count_view)
    private val todayCases by bind<TextView>(R.id.today_cases_count_view)
    private val deaths by bind<TextView>(R.id.deaths_count_view)
    private val todayDeaths by bind<TextView>(R.id.today_deaths_count_view)
    private val recovered by bind<TextView>(R.id.recovered_count_view)
    private val active by bind<TextView>(R.id.active_count_view)
    private val critical by bind<TextView>(R.id.critical_count_view)

    override fun bind() {
        detailsContainer.visibility = View.VISIBLE
        cases.text = "${global.cases}"
        todayCases.text = "${global.todayCases}"
        deaths.text = "${global.deaths}"
        todayDeaths.text = "${global.todayCases}"
        recovered.text = "${global.recovered}"
        active.text = "${global.active}"
        critical.text = "${global.critical}"
    }
}