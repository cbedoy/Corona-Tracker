package iambedoy.coronatracker.adapter.jhu

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import iambedoy.coronatracker.R
import iambedoy.coronatracker.dto.JHUCountryState
import iambedoy.deathsColor
import iambedoy.secondaryTextColor
import iambedoy.totalCasesColor
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.jhu_country_state_header.*
import kotlinx.android.synthetic.main.jhu_country_state_item.*

/**
 * Corona Tracker
 *
 * Created by bedoy on 29/05/20.
 */
sealed class JHUViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    abstract fun bindSource(source: JHUItemType)

    class HeaderViewHolder(override val containerView: View) : JHUViewHolder(containerView){
        override fun bindSource(source: JHUItemType) {
            source as JHUItemType.Header
            jhu_country_name.text = source.countryName
        }
    }

    class ItemViewHolder(override val containerView: View) : JHUViewHolder(containerView){


        override fun bindSource(source: JHUItemType) {
            source as JHUItemType.Item
            jhu_state_name.text = source.countryState.format()
        }

        private fun JHUCountryState.format(): SpannableString {

            val localCases = "${stats.confirmed}"
            val localName : String = province?:""
            val localDeaths = "${stats.deaths}"

            return SpannableString("$localCases $localName ($localDeaths)").apply {
                setSpan(ForegroundColorSpan(containerView.totalCasesColor()), 0, localCases.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(ForegroundColorSpan(containerView.secondaryTextColor()), indexOf(localName), indexOf(localName) + localName.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(ForegroundColorSpan(containerView.deathsColor()), indexOf(localDeaths), indexOf(localDeaths) + localDeaths.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }
}