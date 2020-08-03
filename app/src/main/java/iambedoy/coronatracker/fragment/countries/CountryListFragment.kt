package iambedoy.coronatracker.fragment.countries

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
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import iambedoy.coronatracker.Filter
import iambedoy.coronatracker.R
import iambedoy.coronatracker.viewmodel.CoronaViewModel
import iambedoy.px
import kotlinx.android.synthetic.main.fragment_corona.*
import org.koin.android.ext.android.inject

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CountryListFragment : Fragment(){

    private val viewModel by inject<CoronaViewModel>()
    private val adapter = GroupAdapter<GroupieViewHolder>()

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

        common_recycler_view.layoutManager = LinearLayoutManager(context)
        common_recycler_view.adapter = adapter

        common_progress_bar.visibility = View.VISIBLE

        viewModel.items.observe(viewLifecycleOwner, Observer { items ->
            adapter.addAll(items)
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

        viewModel.loadCountryList()
    }

    private fun sortBy(filter: Filter) {
        viewModel.loadCountryList(filter)
    }

    private fun onQuery(query: String?) {
        viewModel.filterCoronaListWithQuery(query = query?:"")
    }

    private val messageText by lazy {
        val textView = TextView(context)
        val spannableString = SpannableString(
            "- Carlos Bedoy | Android Engineer \n\n " +
                    "https://www.linkedin.com/in/carlos-bedoy-34248187\n" +
                    "http://cbedoy.github.io\n\n" +
                    "Line by line, code by code, logic and syntax, my dream explodes."
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
}