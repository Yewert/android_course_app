package com.example.hw03

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), IHabitInteractionsHandler,
    IDrawerLocker, IFilterHandler, NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var navDrawerLayout: DrawerLayout
    private val model: HabitsViewModel by viewModels()
    private val typedObservers: MutableMap<String, IHabitsListObserver> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        navDrawerLayout = findViewById(R.id.nav_drawer_layout)

        drawerToggle = ActionBarDrawerToggle(
            this,
            navDrawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerToggle.isDrawerIndicatorEnabled = true
        navDrawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        nav_drawer.setNavigationItemSelectedListener(this)

        if (savedInstanceState != null) return

        setHabitsView()
    }

    override fun setFilter(filter: Filter) {
        model.setFilter(filter)
    }

    override fun unsetFilter() {
        model.setFilter(null)
    }

    private fun setHabitsView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HabitsViewFragment()).commit()
    }

    private fun setInfoView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AppInfoFragment()).commit()
    }

    override fun handleEdit(habit: Habit) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HabitEditFragment.newInstance(habit))
            .addToBackStack(null)
            .commit()
    }

    override fun handleNew() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HabitEditFragment.newInstance(Habit.empty))
            .addToBackStack(null)
            .commit()
    }

    override fun handleSave() {
        supportFragmentManager.popBackStack()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_home -> setHabitsView()
            R.id.menu_item_about -> setInfoView()
        }

        navDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun setDrawerEnabled(enabled: Boolean) {
        val lockMode =
            if (enabled) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        navDrawerLayout.setDrawerLockMode(lockMode)
        drawerToggle.isDrawerIndicatorEnabled = enabled
    }
}

interface IEditHabitHandler {
    fun handleEdit(habit: Habit)
}

interface INewHabitHandler {
    fun handleNew()
}

interface ISaveHabitHandler {
    fun handleSave()
}

interface IHabitInteractionsHandler : IEditHabitHandler, INewHabitHandler, ISaveHabitHandler

interface IDrawerLocker {
    fun setDrawerEnabled(enabled: Boolean)
}

interface IFilterHandler {
    fun setFilter(filter: Filter)
    fun unsetFilter()
}