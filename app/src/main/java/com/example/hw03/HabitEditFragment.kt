package com.example.hw03

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hw03.databinding.FragmentHabitEditBinding
import kotlinx.android.synthetic.main.fragment_habit_edit.*

class HabitEditFragment : Fragment() {

    private lateinit var saveHandler: ISaveHabitHandler

    private lateinit var model: HabitEditViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        saveHandler = requireActivity() as ISaveHabitHandler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitEditViewModel(requireActivity().application).initWith(
                    arguments!!.getParcelable(
                        "habit"
                    )!!
                ) as T
            }
        }).get(HabitEditViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHabitEditBinding>(
            inflater,
            R.layout.fragment_habit_edit, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = model
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (requireActivity() as IDrawerLocker).setDrawerEnabled(false)


        habit_type_edit.check(typeToId(model.type) ?: R.id.habit_type_selector_1)

        habit_edit_save.setOnClickListener {
            if (isValid()) {

                val type = view.findViewById<RadioButton>(habit_type_edit.checkedRadioButtonId)
                    .text.toString()
                model.type = type

                model.save()
                saveHandler.handleSave()
            }
        }
    }

    private fun typeToId(type: String): Int? {
        return when (type) {
            "Good" -> R.id.habit_type_selector_1
            "Bad" -> R.id.habit_type_selector_2
            else -> null
        }
    }

    private fun isValid(): Boolean {
        var valid = true
        if (model.name.isBlank()) {
            habit_name_edit.error = "invalid"
            valid = false
        }
        if (model.description.isBlank()) {
            habit_description_edit.error = "invalid"
            valid = false
        }

        return valid
    }

    public override fun onDestroy() {
        super.onDestroy()

        (requireActivity() as IDrawerLocker).setDrawerEnabled(true)
    }

    companion object {
        fun newInstance(habit: Habit) = HabitEditFragment().apply {
            arguments = Bundle().apply { putParcelable("habit", habit) }
        }
    }
}
