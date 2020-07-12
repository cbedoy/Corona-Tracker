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
import coil.api.load
import iambedoy.coronatracker.Filter
import iambedoy.coronatracker.R
import iambedoy.coronatracker.viewmodel.CoronaViewModel
import iambedoy.formattedCountry
import iambedoy.px
import iambedoy.setCollapseExpand
import kotlinx.android.synthetic.main.country_cell.*
import kotlinx.android.synthetic.main.details_view.*
import kotlinx.android.synthetic.main.fragment_corona.*
import org.koin.android.ext.android.inject
import zlc.season.yasha.linear

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CountryListFragment : Fragment(){

    private val viewModel by inject<CoronaViewModel>()

    private val dataSource = CountryListDataSource()

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
        common_recycler_view.isNestedScrollingEnabled = false

        common_recycler_view.linear(
            dataSource = dataSource,
            block = {
                renderItem<GlobalItem> {
                    res(R.layout.global_cell)

                    onBind {
                        details_container.visibility = View.VISIBLE
                        cases_count_view.text = "${data.global.cases}"
                        today_cases_count_view.text = "${data.global.todayCases}"
                        deaths_count_view.text = "${data.global.deaths}"
                        today_deaths_count_view.text = "${data.global.todayCases}"
                        recovered_count_view.text = "${data.global.recovered}"
                        active_count_view.text = "${data.global.active}"
                        critical_count_view.text = "${data.global.critical}"
                    }
                }
                renderItem<CountryItem> {
                    res(R.layout.country_cell)

                    onBind {
                        country_name_view.text = data.country.formattedCountry(country_name_view)
                        country_name_view.setCollapseExpand(details_container, {

                        }, {

                        })
                        country_flag_view.load(data.country.countryInfo?.flag)
                        cases_count_view.text = "${data.country.cases}"
                        today_cases_count_view.text = "${data.country.todayCases}"
                        deaths_count_view.text = "${data.country.deaths}"
                        today_deaths_count_view.text = "${data.country.todayCases}"
                        recovered_count_view.text = "${data.country.recovered}"
                        active_count_view.text = "${data.country.active}"
                        critical_count_view.text = "${data.country.critical}"
                    }
                }
            }
        )
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            dataSource.cleanUp()
            dataSource.addItems(
                countries.map {
                    CountryItem(it)
                }
            )
            common_progress_bar.visibility = View.GONE
        })
        viewModel.global.observe(viewLifecycleOwner, Observer {  global ->
            dataSource.addHeader(GlobalItem(global))
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