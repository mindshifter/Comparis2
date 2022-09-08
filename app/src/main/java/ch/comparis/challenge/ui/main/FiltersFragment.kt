package ch.comparis.challenge.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.comparis.challenge.databinding.FiltersBottomSheetBinding
import ch.comparis.challenge.model.FiltersViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FiltersFragment : BottomSheetDialogFragment() {

    private val filtersViewModel: FiltersViewModel by sharedViewModel()
    private var binding: FiltersBottomSheetBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragmentBinding = FiltersBottomSheetBinding.inflate(layoutInflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filtersViewModel.filter.observe(this) {
            binding?.apply {
                showFavorites.isChecked = it.showFavorite
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            saveFilter.setOnClickListener {
                filtersViewModel.updateFilter()
                this@FiltersFragment.dismiss()
            }
            filterMakes.setOnClickListener {
                showFilterByMakesDialog()
            }
            resetFilter.setOnClickListener {
                filtersViewModel.resetFilter()
                this@FiltersFragment.dismiss()
            }
            showFavorites.setOnCheckedChangeListener { _, isChecked ->
                filtersViewModel.filterFavorites(isChecked)
            }
            showSelectedMakes()
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
                binding?.apply {
                    filtersViewModel.updateMakesFilter(makesMap.filter { it.isSelected }.toMutableList())
                    showSelectedMakes()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create().show()
    }

    private fun showSelectedMakes() {
        binding?.apply {
            selectedMakes.text = ""
            selectedMakes.text = filtersViewModel.getFilterSelectedMakes().joinToString {
                it.name
            }
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