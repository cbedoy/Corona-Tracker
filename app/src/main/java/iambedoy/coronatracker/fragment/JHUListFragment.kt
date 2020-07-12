package iambedoy.coronatracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import iambedoy.coronatracker.R
import iambedoy.coronatracker.adapter.jhu.JHUAdapter
import iambedoy.coronatracker.viewmodel.CoronaViewModel
import kotlinx.android.synthetic.main.fragment_corona.*
import org.koin.android.ext.android.inject

/**
 * Corona Tracker
 *
 * Created by bedoy on 28/05/20.
 */
class JHUListFragment : Fragment(){
    private val adapter = JHUAdapter()
    private val viewModel by inject<CoronaViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_corona, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(context)

        common_progress_bar.visibility = View.VISIBLE
        common_recycler_view.adapter = adapter
        common_recycler_view.layoutManager = linearLayoutManager
        common_recycler_view.isNestedScrollingEnabled = false

        viewModel.states.observe(viewLifecycleOwner, Observer { countries ->
            adapter.setDataSource(countries)
            common_progress_bar.visibility = View.GONE
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadJHUData()
    }
}