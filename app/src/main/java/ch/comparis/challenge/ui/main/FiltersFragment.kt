package ch.comparis.challenge.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ch.comparis.challenge.R
import ch.comparis.challenge.databinding.FiltersBottomSheetBinding
import ch.comparis.challenge.model.CarsFilter
import ch.comparis.challenge.model.FiltersViewModel
import ch.comparis.challenge.model.Make
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FiltersFragment : BottomSheetDialogFragment() {

    private lateinit var selectedMakes: MutableList<Make>
    private val filtersViewModel: FiltersViewModel by sharedViewModel()
    private var binding: FiltersBottomSheetBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d("FiltersFragment", "onCreateView")
        val fragmentBinding = FiltersBottomSheetBinding.inflate(layoutInflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("FiltersFragment", "onCreate")
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("FiltersFragment", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        filtersViewModel.filter.observe(viewLifecycleOwner) {
            binding?.apply {
                showFavorites.isChecked = it.showFavorite
                selectedMakes = it.makes
                mileageFrom.editText?.setText(it.mileageFrom.toString())
                mileageTo.editText?.setText(it.mileageTo.toString())
                showSelectedMakes(selectedMakes)
            }
        }
        binding?.apply {
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

    private fun saveFilter() {
        binding?.apply {
            val carsFilter = CarsFilter()
            carsFilter.showFavorite = showFavorites.isChecked
            carsFilter.mileageFrom = mileageFrom.editText?.text.toString().toInt()
            carsFilter.mileageTo = mileageTo.editText?.text.toString().toInt()
            carsFilter.makes = selectedMakes
            filtersViewModel.updateFilter(carsFilter)
        }
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
                selectedMakes = makesMap.filter { it.isSelected }.toMutableList()
                showSelectedMakes(selectedMakes)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create().show()
    }

    private fun showSelectedMakes(selectedMakes: MutableList<Make>) {
        binding?.apply {
            selectedMakesTextView.text = ""
            selectedMakesTextView.text = selectedMakes.joinToString { it.name }
        }
    }

    companion object {
        fun newInstance() = FiltersFragment()
        const val TAG = "CustomBottomSheetDialogFragment"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}