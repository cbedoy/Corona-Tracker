package iambedoy.coronatracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import iambedoy.coronatracker.R
import iambedoy.coronatracker.databinding.CountrySmallCellBinding
import iambedoy.coronatracker.dto.Country

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaAdapter : RecyclerView.Adapter<CountryHolder>(){

    var dataModel = mutableListOf<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        return CountryHolder(
            binding = CountrySmallCellBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return dataModel.size
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.bind(dataModel[position])
    }
}

