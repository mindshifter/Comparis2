package ch.comparis.challenge.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import ch.comparis.challenge.R
import ch.comparis.challenge.databinding.FiltersBottomSheetBinding
import ch.comparis.challenge.model.CarsFilter
import ch.comparis.challenge.model.CarsFilter.Companion.MAX_MILEAGE
import ch.comparis.challenge.model.CarsFilter.Companion.MIN_MILEAGE
import ch.comparis.challenge.model.FiltersViewModel
import ch.comparis.challenge.model.SelectedMake
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FiltersFragment : BottomSheetDialogFragment() {

    private var currentSelectedMakes: MutableList<SelectedMake> = mutableListOf()
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
        filtersViewModel.filter.observe(requireActivity(), this@FiltersFragment::showFilters)
        with(binding) {
            saveFilterButton.setOnClickListener { saveFilter() }
            filterMakes.setOnClickListener { showFilterByMakesDialog() }
            resetFilterButton.setOnClickListener { showConfirmResetFilterDialog() }
            clearMakes.setOnClickListener { clearMakesFilter() }
        }
    }

    private fun clearMakesFilter() {
        showSelectedMakes(mutableListOf())
    }

    private fun showConfirmResetFilterDialog() {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Reset Filter")
            .setMessage("Are you sure you want to reset all the search criteria?")
            .setPositiveButton("Yes") { dialog, _ ->
                filtersViewModel.resetFilter()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .create().show()
    }

    private fun showFilters(carsFilter: CarsFilter) {
        binding.showFavorites.isChecked = carsFilter.showFavorite
        showMileageFrom(carsFilter.mileageFrom)
        showMileageTo(carsFilter.mileageTo)
        showSelectedMakes(carsFilter.selectedMakes)
    }

    private fun showMileageFrom(mileageFrom: Int) {
        binding.mileageFrom.editText?.setText(if (mileageFrom != MIN_MILEAGE) mileageFrom.toString() else "")
    }

    private fun showMileageTo(mileageTo: Int) {
        binding.mileageTo.editText?.setText(if (mileageTo != MAX_MILEAGE) mileageTo.toString() else "")
    }

    private fun saveFilter() {
        with(binding) {
            val carsFilter = CarsFilter()
            carsFilter.showFavorite = showFavorites.isChecked
            carsFilter.mileageFrom = getMileageFrom()
            carsFilter.mileageTo = getMileageTo()
            carsFilter.selectedMakes = currentSelectedMakes
            filtersViewModel.updateFilter(carsFilter)
        }
        this@FiltersFragment.dismiss()
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
        var allMakes = filtersViewModel.getAvailableMakes()
        allMakes = listOf(currentSelectedMakes + allMakes).flatten().distinctBy { it.make.id }.sortedBy { it.make.id }
        val makesNamesArray = allMakes.map { it.make.name }.toTypedArray()
        val checkedMakesArray = allMakes.map { it.isSelected }.toBooleanArray()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.filter_makes))
            .setMultiChoiceItems(makesNamesArray, checkedMakesArray) { _, which, isChecked ->
                allMakes[which].isSelected = isChecked
            }
            .setPositiveButton("OK") { dialog, _ ->
                showSelectedMakes(allMakes.filter { it.isSelected }.toMutableList())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    private fun showSelectedMakes(makes: MutableList<SelectedMake>) {
        currentSelectedMakes = makes
        with(binding) {
            clearMakes.isVisible = currentSelectedMakes.any { it.isSelected }
            selectMakesIcon.isVisible = currentSelectedMakes.none { it.isSelected }
            selectedMakesTextView.text = ""
            selectedMakesTextView.text = currentSelectedMakes.joinToString { it.make.name }
        }
    }

    companion object {
        fun newInstance() = FiltersFragment()
        const val TAG = "CustomBottomSheetDialogFragment"
    }
}