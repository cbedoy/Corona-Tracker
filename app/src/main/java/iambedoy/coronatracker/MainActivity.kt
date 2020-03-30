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
import iambedoy.coronatracker.models.SortBy
import iambedoy.coronatracker.models.SortBy.*

class MainActivity : AppCompatActivity() {

    private val messageText by lazy {
        val textView = TextView(this)
        val spannableString = SpannableString(
            "Inspired by: \n" +
                    "https://github.com/ahmadawais/corona-cli\n" +
                    "\n" +
                    "\n" +
                    "\uD83E\uDDA0 Track Corona Virus COVID19 cases with this Andriod Application.\n" +
                    "\n" +
                    "\n" +
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

    private val fragment by lazy {
        CoronaListFragment()
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
            R.id.action_filter_by_cases -> fragment.sortBy(CASES)
            R.id.action_filter_by_deaths -> fragment.sortBy(DEATHS)
            R.id.action_filter_by_recovered -> fragment.sortBy(RECOVERED)
            R.id.action_filter_by_today_cases -> fragment.sortBy(TODAY_CASES)
            R.id.action_filter_by_today_deaths -> fragment.sortBy(TODAY_DEATHS)
        }
        return super.onOptionsItemSelected(item)
    }

    val Int.dp: Int
        get() = (this / Resources.getSystem().displayMetrics.density).toInt()
    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}
