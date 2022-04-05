package co.instil.mymet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import co.instil.mymet.R
import co.instil.mymet.ui.utils.NoSwipeViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
        setUpViewPager()
    }

    private fun setupNavigation() {
        findViewById<BottomNavigationView>(R.id.navigation).setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_all -> {
                    title = getString(R.string.app_name)
                    findViewById<NoSwipeViewPager>(R.id.viewPager).setCurrentItem(0, false)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorites -> {
                    title = getString(R.string.favorites)
                   findViewById<NoSwipeViewPager>(R.id.viewPager).setCurrentItem(1, false)
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }

    private fun setUpViewPager() {
        findViewById<NoSwipeViewPager>(R.id.viewPager).adapter = object : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment {
                if (position == 1) {
                    return FavoritesFragment.newInstance()
                }
                return AllFragment.newInstance()
            }

            override fun getCount() = 2
        }
    }
}
