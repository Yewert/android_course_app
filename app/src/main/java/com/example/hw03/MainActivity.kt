package com.example.hw03

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

interface IHabitsListObservable {
    fun attachObserver(observer: IHabitsListObserver)
}

class MainActivity : AppCompatActivity(), IHabitInteractionsHandler, IHabitsListObservable {
    private val model: HabitsViewModel by viewModels()
    private val typedObservers: MutableMap<String, IHabitsListObserver> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) return
        model.addOrUpdate(Habit("one", "descr", 1, "Good", 1, 1))
        model.addOrUpdate(Habit("one", "descr", 1, "Bad", 1, 1))
        setContentView(R.layout.main_activity)

        setSupportActionBar(findViewById(R.id.toolbar))

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, HabitsViewFragment()).commit()
    }

    override fun attachObserver(observer: IHabitsListObserver) {
        typedObservers[observer.type] = observer
    }

    override fun handleEdit(habit: Habit) {
        supportFragmentManager.beginTransaction()
            .hide(supportFragmentManager.findFragmentById(R.id.fragment_container)!!)
            .add(R.id.fragment_container, HabitEditFragment.newInstance(habit)).addToBackStack(null)
            .commit()
    }

    override fun handleNew() {
        supportFragmentManager.beginTransaction()
            .hide(supportFragmentManager.findFragmentById(R.id.fragment_container)!!)
            .add(R.id.fragment_container, HabitEditFragment.newInstance(Habit.default))
            .addToBackStack(null)
            .commit()
    }

    override fun handleSave(habit: Habit, originalType: String) {
        supportFragmentManager.popBackStack()
        model.addOrUpdate(habit)
        if (originalType != habit.type)
            typedObservers[originalType]!!.onHabitRemoved(habit.id)
        typedObservers[habit.type]?.onHabitChanged(habit.id)
    }
}

interface IEditHabitHandler {
    fun handleEdit(habit: Habit)
}

interface INewHabitHandler {
    fun handleNew()
}

interface ISaveHabitHandler {
    fun handleSave(habit: Habit, originalType: String)
}

interface IHabitInteractionsHandler : IEditHabitHandler, INewHabitHandler, ISaveHabitHandler