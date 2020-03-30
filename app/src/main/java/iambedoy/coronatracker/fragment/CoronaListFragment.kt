package iambedoy.coronatracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import iambedoy.coronatracker.R
import iambedoy.coronatracker.adapter.CoronaAdapter
import iambedoy.coronatracker.models.SortBy
import iambedoy.coronatracker.viewmodel.CoronaViewModel
import kotlinx.android.synthetic.main.fragment_corona.*

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaListFragment : Fragment(){

    private val coronaAdapter by lazy {
        CoronaAdapter()
    }

    private val viewModel : CoronaViewModel by viewModels()

    private var currentSortBy = SortBy.CASES

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

        viewModel.coronaList.observe(viewLifecycleOwner, Observer { list ->
            coronaAdapter.dataModel = list
            coronaAdapter.notifyDataSetChanged()

            coronaAdapter.currentSortBy = currentSortBy
        })
     }

    override fun onResume() {
        super.onResume()

        viewModel.loadCoronaList()
    }

    fun sortBy(sort: SortBy) {
        currentSortBy = sort
        viewModel.filterBy(sort)
    }
}