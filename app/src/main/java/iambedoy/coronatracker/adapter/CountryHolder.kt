package iambedoy.coronatracker.adapter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import iambedoy.coronatracker.R
import iambedoy.coronatracker.databinding.CountrySmallCellBinding
import iambedoy.coronatracker.dto.Country
import iambedoy.coronatracker.setCollapseExpand

/**
 * Corona Tracker
 *
 * Created by bedoy on 20/05/20.
 */
class CountryHolder(private val binding: CountrySmallCellBinding) : RecyclerView.ViewHolder(binding.root) {

    private val casesColor : Int
        get() = ContextCompat.getColor(binding.root.context, R.color.total_cases_color)

    private val deathsColor : Int
        get() = ContextCompat.getColor(binding.root.context, R.color.total_deaths_color)

    private val primaryTextColor : Int
        get() = ContextCompat.getColor(binding.root.context, R.color.black_primary)

    fun bind(country: Country){
        binding.country = country
        binding.coronaSmallCellCountryName.setCollapseExpand(binding.coronaSmallCellInfoContainer, {

        },{

        })
        binding.coronaSmallCellCountryName.text = country.formattedCountry()
    }


    companion object{
        @JvmStatic
        @BindingAdapter("flagUrl")
        fun loadImage(imageView: ImageView, flagUrl: String){
            imageView.load(flagUrl)
        }
    }

    private fun Country.formattedCountry(): SpannableString {

        val localCases = "$cases"
        val localName : String = country?:""
        val localDeaths = "$deaths"

        return SpannableString("$localCases $localName ($localDeaths)").apply {
            setSpan(ForegroundColorSpan(casesColor), 0, localCases.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(primaryTextColor), indexOf(localName), indexOf(localName) + localName.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(deathsColor), indexOf(localDeaths), indexOf(localDeaths) + localDeaths.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
}


