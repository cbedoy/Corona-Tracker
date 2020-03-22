package iambedoy.coronatracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_corona, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_corona_recycler_view.layoutManager = LinearLayoutManager(context)
        fragment_corona_recycler_view.adapter = coronaAdapter
        fragment_corona_recycler_view.setHasFixedSize(true)

        viewModel.coronaList.observe(viewLifecycleOwner, Observer { list ->
            coronaAdapter.dataModel = list
            coronaAdapter.notifyDataSetChanged()
        })
     }

    override fun onResume() {
        super.onResume()

        viewModel.loadCoronaList()
    }
}