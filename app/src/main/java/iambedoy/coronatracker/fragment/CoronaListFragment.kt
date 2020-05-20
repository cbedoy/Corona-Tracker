package iambedoy.coronatracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import iambedoy.coronatracker.Filter
import iambedoy.coronatracker.R
import iambedoy.coronatracker.adapter.CoronaAdapter
import iambedoy.coronatracker.viewmodel.CoronaViewModel
import kotlinx.android.synthetic.main.fragment_corona.*
import org.koin.android.ext.android.inject

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaListFragment : Fragment(){

    private val coronaAdapter = CoronaAdapter()
    private val viewModel by inject<CoronaViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_corona, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(context)

        fragment_corona_recycler_view.layoutManager = linearLayoutManager
        fragment_corona_recycler_view.adapter = coronaAdapter
        fragment_corona_recycler_view.isNestedScrollingEnabled = false

        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            coronaAdapter.dataModel.clear()
            coronaAdapter.dataModel.addAll(countries)
            coronaAdapter.notifyDataSetChanged()
        })
     }

    override fun onResume() {
        super.onResume()

        viewModel.loadCoronaList()
    }

    fun sortBy(filter: Filter) {
        viewModel.loadCoronaList(filter)
    }
}