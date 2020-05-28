package iambedoy.coronatracker.adapter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import iambedoy.coronatracker.R
import iambedoy.coronatracker.adapter.JHUItemType.*
import iambedoy.coronatracker.dto.JHUCountryState
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.jhu_country_state_header.*
import kotlinx.android.synthetic.main.jhu_country_state_item.*

/**
 * Corona Tracker
 *
 * Created by bedoy on 28/05/20.
 */
class JHUAdapter : RecyclerView.Adapter<JHUViewHolder>() {

    private var dataSource = mutableListOf<JHUItemType>()

    fun setDataSource(map: Map<String, MutableList<JHUCountryState>>){
        dataSource.clear()
        map.map { (key, value) ->
            dataSource.add(Header(key))
            dataSource.addAll(value.map {
                Item(it)
            })
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JHUViewHolder {
        return if (viewType == 0){
            JHUViewHolder.HeaderViewHolder(
                parent.inflate(R.layout.jhu_country_state_header)
            )
        }else{
            JHUViewHolder.ItemViewHolder(
                parent.inflate(R.layout.jhu_country_state_item)
            )
        }
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(dataSource[position]){
            is Header -> 0
            is Item -> 1
        }
    }

    override fun onBindViewHolder(holder: JHUViewHolder, position: Int) {
        holder.bindSource(dataSource[position])
    }
}

sealed class JHUItemType {
    data class Header(val countryName: String) : JHUItemType()
    data class Item(val countryState: JHUCountryState) : JHUItemType()
}

sealed class JHUViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{
    abstract fun bindSource(source: JHUItemType)

    class HeaderViewHolder(override val containerView: View) : JHUViewHolder(containerView){
        override fun bindSource(source: JHUItemType) {
            source as Header
            jhu_country_name.text = source.countryName
        }
    }

    class ItemViewHolder(override val containerView: View) : JHUViewHolder(containerView){

        private val casesColor : Int
            get() = ContextCompat.getColor(containerView.context, R.color.total_cases_color)

        private val deathsColor : Int
            get() = ContextCompat.getColor(containerView.context, R.color.total_deaths_color)

        private val secondaryTextColor : Int
            get() = ContextCompat.getColor(containerView.context, R.color.black_secondary)

        override fun bindSource(source: JHUItemType) {
            source as Item
            jhu_state_name.text = source.countryState.format()
        }

        private fun JHUCountryState.format(): SpannableString {

            val localCases = "${stats.confirmed}"
            val localName : String = province?:""
            val localDeaths = "${stats.deaths}"

            return SpannableString("$localCases $localName ($localDeaths)").apply {
                setSpan(ForegroundColorSpan(casesColor), 0, localCases.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(ForegroundColorSpan(secondaryTextColor), indexOf(localName), indexOf(localName) + localName.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(ForegroundColorSpan(deathsColor), indexOf(localDeaths), indexOf(localDeaths) + localDeaths.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }
}

fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}