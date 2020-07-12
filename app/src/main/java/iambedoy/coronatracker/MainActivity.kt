package iambedoy.coronatracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import iambedoy.coronatracker.fragment.countries.CountryListFragment
import iambedoy.coronatracker.fragment.CountryFragment
import iambedoy.coronatracker.fragment.JHUListFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val fragment by inject<CountryListFragment>()
    private val jhuListFragment by inject<JHUListFragment>()
    private val countryFragment by inject<CountryFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_countries -> showFragment(fragment)
                R.id.action_states -> showFragment(jhuListFragment)
                R.id.action_mex -> showFragment(countryFragment)
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.action_countries
    }

    private fun showFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.layout_container, fragment).commit()
    }
}
