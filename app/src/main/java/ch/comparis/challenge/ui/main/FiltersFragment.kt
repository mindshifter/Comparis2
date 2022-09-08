package ch.comparis.challenge.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.comparis.challenge.databinding.FiltersBottomSheetBinding
import ch.comparis.challenge.model.CarsViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            firstButton.setOnClickListener {
                showFilterByMakesDialog()
            }
            showFavorites.isChecked = filtersViewModel.carFilter.showFavorite
            showFavorites.setOnCheckedChangeListener { _, isChecked ->
                filtersViewModel.filterFavorites(isChecked)
            }
        }
    }

    private fun showFilterByMakesDialog() {
        val makesMap = filtersViewModel.getMakes()
        val makesNamesArray = makesMap.map { it.make.name }.toTypedArray()
        val checkedMakesArray = makesMap.map { it.isSelected }.toBooleanArray()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Filter Makes")
            .setMultiChoiceItems(makesNamesArray, checkedMakesArray) { _, which, isChecked ->
                checkedMakesArray[which] = isChecked
            }
            .setPositiveButton("OK") { dialog, _ ->
                binding?.apply {
                    selectedMakes.text = "Your preferred colors..... \n"
                    checkedMakesArray.forEachIndexed { index, isChecked ->
                        if (isChecked) {
                            selectedMakes.text = selectedMakes.text.toString() + makesNamesArray[index] + "\n"
                        }
                    }
                    filtersViewModel.updateMakesFilter(checkedMakesArray)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create().show()
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