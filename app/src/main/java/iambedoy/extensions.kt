package iambedoy

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import iambedoy.coronatracker.R
import iambedoy.coronatracker.dto.Country

/**
 * Corona Tracker
 *
 * Created by bedoy on 29/05/20.
 */

fun TextView.setCollapseExpand(collapsibleView: View, onCollapse: () -> Unit, onExpand: () -> Unit) {
    setCompoundDrawablesWithIntrinsicBounds(null, null, createDrawable(R.drawable.ic_action_expand), null)
    setOnClickListener {
        when (collapsibleView.visibility) {
            View.GONE -> {
                collapsibleView.visibility = View.VISIBLE
                setCompoundDrawablesWithIntrinsicBounds(null, null, createDrawable(R.drawable.ic_action_collapse), null)
                onExpand()
            }
            View.VISIBLE -> {
                collapsibleView.visibility = View.GONE
                setCompoundDrawablesWithIntrinsicBounds(null, null, createDrawable(R.drawable.ic_action_expand), null)
                onCollapse()
            }
        }
    }
}

fun View.createDrawable(resourceId : Int): Drawable? {
    return ContextCompat.getDrawable(context, resourceId)
}

fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}

fun TextView.setTotalCasesColor() = setTextColor(ContextCompat.getColor(context, R.color.total_cases_color))
fun TextView.setDeathsColor() = setTextColor(ContextCompat.getColor(context, R.color.total_deaths_color))
fun TextView.setPrimaryTextColor() = setTextColor(ContextCompat.getColor(context, R.color.black_primary))

fun View.totalCasesColor() = ContextCompat.getColor(context, R.color.total_cases_color)
fun View.deathsColor() = ContextCompat.getColor(context, R.color.total_deaths_color)
fun View.primaryTextColor() = ContextCompat.getColor(context, android.R.color.white)
fun View.secondaryTextColor() = ContextCompat.getColor(context, android.R.color.white)

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Country.formattedCountry(view: View): SpannableString {
    val localCases = "$cases"
    val localName : String = country?:""
    val localDeaths = "$deaths"

    return SpannableString("$localCases $localName ($localDeaths)").apply {
        setSpan(ForegroundColorSpan(view.totalCasesColor()), 0, localCases.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(ForegroundColorSpan(view.primaryTextColor()), indexOf(localName), indexOf(localName) + localName.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(ForegroundColorSpan(view.deathsColor()), lastIndexOf(localDeaths), lastIndexOf(localDeaths) + localDeaths.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}