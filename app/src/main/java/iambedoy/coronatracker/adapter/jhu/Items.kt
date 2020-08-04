package iambedoy.coronatracker.adapter.jhu

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import iambedoy.coronatracker.R
import iambedoy.coronatracker.dto.JHUCountryState
import iambedoy.deathsColor
import iambedoy.secondaryTextColor
import iambedoy.totalCasesColor
import kotlinx.android.synthetic.main.jhu_country_state_header.*
import kotlinx.android.synthetic.main.jhu_country_state_item.*

/**
 * Corona Tracker
 *
 * Created by bedoy on 29/05/20.
 */
class TitleItem(private val countryName: String) : Item(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.jhu_country_name.text = countryName
    }

    override fun getLayout(): Int {
        return R.layout.jhu_country_state_header
    }

}

class StateItem(private val countryState: JHUCountryState) : Item(){

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.jhu_state_name.text = countryState.format(viewHolder.containerView)
    }

    override fun getLayout(): Int {
        return R.layout.jhu_country_state_item
    }

    private fun JHUCountryState.format(containerView: View): SpannableString {

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