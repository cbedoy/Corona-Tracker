package iambedoy.coronatracker.fragment

import android.content.res.Resources
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.*
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_corona, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(context)

        common_progress_bar.visibility = View.VISIBLE
        common_recycler_view.layoutManager = linearLayoutManager
        common_recycler_view.adapter = coronaAdapter
        common_recycler_view.isNestedScrollingEnabled = false

        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            coronaAdapter.dataModel.clear()
            coronaAdapter.dataModel.addAll(countries)
            coronaAdapter.notifyDataSetChanged()
            common_progress_bar.visibility = View.GONE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        (menu.findItem(R.id.action_search)?.actionView as? SearchView)?.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {

                    onQuery(query)

                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    onQuery(query)

                    return true
                }
            })
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_info -> {
                activity?.let {
                    AlertDialog.Builder(it).setView(messageText).show()
                }
            }
            R.id.action_filter_by_cases -> sortBy(Filter.cases)
            R.id.action_filter_by_deaths -> sortBy(Filter.deaths)
            R.id.action_filter_by_recovered -> sortBy(Filter.recovered)
            R.id.action_filter_by_today_cases -> sortBy(Filter.todayCases)
            R.id.action_filter_by_today_deaths -> sortBy(Filter.todayDeaths)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadCoronaList()
    }

    private fun sortBy(filter: Filter) {
        viewModel.loadCoronaList(filter)
    }

    private fun onQuery(query: String?) {
        viewModel.filterCoronaListWithQuery(query = query?:"")
    }

    private val messageText by lazy {
        val textView = TextView(context)
        val spannableString = SpannableString(
            "- Carlos Bedoy | Android Engineer \n\n " +
                    "https://www.linkedin.com/in/carlos-bedoy-34248187\n" +
                    "http://cbedoy.github.io\n"
        )
        Linkify.addLinks(spannableString, Linkify.WEB_URLS);
        textView.text = spannableString
        textView.setPadding(16.px,16.px,16.px,16.px)
        context?.let {
            textView.setTextColor(ContextCompat.getColor(it, R.color.black_primary))
        }
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView
    }


    val Int.dp: Int
        get() = (this / Resources.getSystem().displayMetrics.density).toInt()
    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}