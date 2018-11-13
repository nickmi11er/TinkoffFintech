package ru.nickmiller.tinkofffintech.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.pnikosis.materialishprogress.ProgressWheel
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.ui.courses.CoursesFragment
import ru.nickmiller.tinkofffintech.ui.events.EventsFragment
import ru.nickmiller.tinkofffintech.ui.profile.ProfileFragment
import ru.nickmiller.tinkofffintech.utils.find

class MainActivity : AppCompatActivity() {
    private val bnv by lazy { find<BottomNavigationView>(R.id.navigation) }
    private val progressBar by lazy { find<ProgressWheel>(R.id.progress_bar) }

    private val profileFragment by lazy { ProfileFragment() }
    private val eventsFragment by lazy { EventsFragment() }
    private val coursesFragment by lazy { CoursesFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BottomNavigationViewHelper.disableShiftMode(bnv)
        bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_courses -> replaceFragment(coursesFragment)
                R.id.item_profile -> replaceFragment(profileFragment)
                else -> replaceFragment(eventsFragment)
            }
        }

        bnv.selectedItemId = R.id.item_events
    }

    private fun replaceFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
        return true
    }

    fun startProgress() = progressBar.spin()

    fun stopProgress() = progressBar.stopSpinning()

    fun showError(errorMsg: String?) {
        progressBar.stopSpinning()
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
    }
}
