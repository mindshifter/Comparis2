package ch.comparis.challenge.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ch.comparis.challenge.R
import ch.comparis.challenge.databinding.FiltersBottomSheetBinding
import ch.comparis.challenge.model.CarsFilter
import ch.comparis.challenge.model.CarsFilter.Companion.MAX_MILEAGE
import ch.comparis.challenge.model.CarsFilter.Companion.MIN_MILEAGE
import ch.comparis.challenge.model.FiltersViewModel
import ch.comparis.challenge.model.Make
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FiltersFragment : BottomSheetDialogFragment() {

    private lateinit var selectedMakes: MutableList<Make>
    private val filtersViewModel: FiltersViewModel by sharedViewModel()
    private lateinit var binding: FiltersBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FiltersBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filtersViewModel.filter.observe(viewLifecycleOwner) {
            showFilters(it)
        }
        with(binding) {
            saveFilterButton.setOnClickListener {
                saveFilter()
                this@FiltersFragment.dismiss()
            }
            filterMakes.setOnClickListener {
                showFilterByMakesDialog()
            }
            resetFilterButton.setOnClickListener {
                filtersViewModel.resetFilter()
                this@FiltersFragment.dismiss()
            }
        }
    }

    private fun showFilters(carsFilter: CarsFilter) {
        binding.showFavorites.isChecked = carsFilter.showFavorite
        showMileageFrom(carsFilter.mileageFrom)
        showMileageTo(carsFilter.mileageTo)
        showSelectedMakes(carsFilter.makes)
    }


    private fun showMileageFrom(mileageFrom: Int) {
        if (mileageFrom != MIN_MILEAGE) {
            binding.mileageFrom.editText?.setText(mileageFrom.toString())
        } else {
            binding.mileageFrom.editText?.setText("")
        }
    }

    private fun showMileageTo(mileageTo: Int) {
        if (mileageTo != MAX_MILEAGE) {
            binding.mileageTo.editText?.setText(mileageTo.toString())
        } else {
            binding.mileageTo.editText?.setText("")
        }
    }

    private fun saveFilter() {
        with(binding) {
            val carsFilter = CarsFilter()
            carsFilter.showFavorite = showFavorites.isChecked
            carsFilter.mileageFrom = getMileageFrom()
            carsFilter.mileageTo = getMileageTo()
            carsFilter.makes = selectedMakes
            filtersViewModel.updateFilter(carsFilter)
        }
    }

    private fun getMileageFrom(): Int {
        val mileageFromString = binding.mileageFrom.editText?.text.toString()
        return if (mileageFromString.isEmpty()) MIN_MILEAGE else mileageFromString.toInt()
    }

    private fun getMileageTo(): Int {
        val mileageToString = binding.mileageTo.editText?.text.toString()
        return if (mileageToString.isEmpty()) MAX_MILEAGE else mileageToString.toInt()
    }

    private fun showFilterByMakesDialog() {
        val makesMap = filtersViewModel.getAllMakes()
        val makesNamesArray = makesMap.map { it.name }.toTypedArray()
        val checkedMakesArray = makesMap.map { it.isSelected }.toBooleanArray()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Filter Makes")
            .setMultiChoiceItems(makesNamesArray, checkedMakesArray) { _, which, isChecked ->
                makesMap[which].isSelected = isChecked
            }
            .setPositiveButton("OK") { dialog, _ ->
                showSelectedMakes(makesMap.filter { it.isSelected }.toMutableList())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create().show()
    }

    private fun showSelectedMakes(makes: MutableList<Make>) {
        selectedMakes = makes
        with(binding) {
            selectedMakesTextView.text = ""
            selectedMakesTextView.text = selectedMakes.joinToString { it.name }
        }
    }

    companion object {
        fun newInstance() = FiltersFragment()
        const val TAG = "CustomBottomSheetDialogFragment"
    }
}