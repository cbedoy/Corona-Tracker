package iambedoy.coronatracker.adapter.jhu

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import iambedoy.coronatracker.R
import iambedoy.coronatracker.adapter.jhu.JHUItemType.Header
import iambedoy.coronatracker.adapter.jhu.JHUItemType.Item
import iambedoy.coronatracker.adapter.jhu.JHUViewHolder.HeaderViewHolder
import iambedoy.coronatracker.adapter.jhu.JHUViewHolder.ItemViewHolder
import iambedoy.coronatracker.dto.JHUCountryState
import iambedoy.inflate

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
            HeaderViewHolder(
                parent.inflate(R.layout.jhu_country_state_header)
            )
        }else{
            ItemViewHolder(
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