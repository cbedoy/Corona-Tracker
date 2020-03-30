package iambedoy.coronatracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import iambedoy.coronatracker.R
import iambedoy.coronatracker.models.Country
import iambedoy.coronatracker.models.Global
import iambedoy.coronatracker.models.SortBy
import iambedoy.coronatracker.setCollapseExpand
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.country_small_cell.*
import kotlinx.android.synthetic.main.disclaimer_cell.*
import kotlinx.android.synthetic.main.global_cell.*

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaAdapter : RecyclerView.Adapter<CoronaAdapter.BaseCoronaHolder>(){

    var dataModel = listOf<Any>()

    private val countryType = 0
    private val disclaimerType = 1
    private val globalType = 2
    private val emptyDataType = 3

    var currentSortBy : SortBy = SortBy.CASES
        set(value) {
            field = value
            notifyItemRangeChanged(1, dataModel.size - 2)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseCoronaHolder {
        when (viewType) {
            countryType -> {
                return CountryHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.country_small_cell,
                        parent,
                        false
                    )
                )
            }
            globalType -> {
                return GlobalHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.global_cell,
                        parent,
                        false
                    )
                )
            }
            emptyDataType -> {
                return EmptyDataHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.disclaimer_cell,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return DisclaimerHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.disclaimer_cell,
                        parent,
                        false
                    )
                )
            }
        }
    }

    fun targetValueFromSort() = when(currentSortBy){
        SortBy.DEATHS -> "Deaths"
        SortBy.RECOVERED -> "Recovered"
        SortBy.TODAY_DEATHS -> "Today  deaths"
        SortBy.TODAY_CASES -> "Today cases"
        SortBy.CASES -> "Cases"
    }

    fun targetColorFromSort() = when(currentSortBy){
        SortBy.DEATHS -> R.color.red
        SortBy.RECOVERED -> R.color.green
        SortBy.TODAY_DEATHS -> R.color.red
        SortBy.TODAY_CASES -> R.color.blue
        SortBy.CASES -> R.color.darkBlue
    }

    fun valueFromSort(country: Country) = when(currentSortBy){
        SortBy.DEATHS -> "${country.deaths}"
        SortBy.RECOVERED -> "${country.recovered}"
        SortBy.TODAY_DEATHS -> "${country.todayDeaths}"
        SortBy.TODAY_CASES -> "${country.todayCases}"
        SortBy.CASES -> "${country.cases}"
    }

    override fun getItemCount(): Int {
        return dataModel.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) globalType else if (position == 1) disclaimerType else  countryType
    }

    override fun onBindViewHolder(holder: BaseCoronaHolder, position: Int) {
        val item : Any? = dataModel[position]
        holder.reloadItem(item)
    }

    inner class DisclaimerHolder(override val containerView: View): BaseCoronaHolder(containerView){
        override fun reloadItem(item: Any?) {
            item as SortBy?

            disclaimer_cell_title.text = when(item){
                SortBy.DEATHS -> "Displayed by deaths order:"
                SortBy.TODAY_DEATHS -> "Displayed by today deaths order:"
                SortBy.CASES -> "Displayed by cases order:"
                SortBy.TODAY_CASES -> "Displayed by today cases order:"
                SortBy.RECOVERED -> "Displayed by recovered order:"
                else -> "Displayed by default order:"
            }
        }
    }

    inner class EmptyDataHolder(override val containerView: View): BaseCoronaHolder(containerView){
        override fun reloadItem(item: Any?) {
            item as String?

            disclaimer_cell_title.text = "Something is wrong:\n\nSorry we're having some trouble trying to retrieve information, please try again later."
        }

    }

    inner class CountryHolder(override val containerView: View): BaseCoronaHolder(containerView) {
        override fun reloadItem(item: Any?) {
            item as Country

            corona_small_cell_country_name.text = item.country

            corona_small_cell_country_flag.load(item.countryInfo.flag)

            corona_cell_cases_per_one_million_text.text = "${item.casesPerOneMillion}"

            corona_cell_cases_text.text = "${item.cases}"

            corona_cell_today_cases_text.text = "${item.todayCases}"

            corona_cell_today_deaths_text.text = "${item.todayDeaths}"

            corona_cell_critical_text.text = "${item.critical}"

            corona_cell_recovered_text.text = "${item.recovered}"

            corona_cell_deaths_text.text = "${item.deaths}"

            corona_cell_active_text.text = "${item.active}"

            corona_small_cell_country_name.setCollapseExpand(corona_small_cell_info_container, {

            },{

            })

            setIsRecyclable(false)
        }

    }

    inner class GlobalHolder(override val containerView: View): BaseCoronaHolder(containerView){
        override fun reloadItem(item: Any?) {
            item as Global

            global_cell_cases_text.text = "${item.cases}"

            global_cell_recovered_text.text = "${item.recovered}"

            global_cell_deaths_text.text = "${item.deaths}"

            global_cell_last_update.text = ""
        }
    }

    abstract inner class BaseCoronaHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{
        abstract fun reloadItem(item: Any?)
    }
}

