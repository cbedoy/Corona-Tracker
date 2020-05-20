package iambedoy.coronatracker

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import iambedoy.coronatracker.fragment.CoronaListFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val fragment by inject<CoronaListFragment>()

    private val messageText by lazy {
        val textView = TextView(this)
        val spannableString = SpannableString(
                    "Android App by:\n" +
                    "- Carlos Bedoy | Android Engineer \n " +
                    "- https://www.linkedin.com/in/carlos-bedoy-34248187\n" +
                    "- http://cbedoy.github.io\n" +
                    "\n" +
                    "Backend \n" +
                    "\n" +
                    "- NovelCOVID https://github.com/NovelCOVID/API \n"
        )
        Linkify.addLinks(spannableString, Linkify.WEB_URLS);
        textView.text = spannableString
        textView.setPadding(16.px,16.px,16.px,16.px)
        textView.setTextColor(ContextCompat.getColor(this, R.color.black_secondary))
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.activity_container,
                fragment
            )
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_info -> {
                AlertDialog.Builder(this).setView(messageText).show()
            }
            R.id.action_filter_by_cases -> fragment.sortBy(Filter.cases)
            R.id.action_filter_by_deaths -> fragment.sortBy(Filter.deaths)
            R.id.action_filter_by_recovered -> fragment.sortBy(Filter.recovered)
            R.id.action_filter_by_today_cases -> fragment.sortBy(Filter.todayCases)
            R.id.action_filter_by_today_deaths -> fragment.sortBy(Filter.todayDeaths)
        }
        return super.onOptionsItemSelected(item)
    }

    val Int.dp: Int
        get() = (this / Resources.getSystem().displayMetrics.density).toInt()
    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}
