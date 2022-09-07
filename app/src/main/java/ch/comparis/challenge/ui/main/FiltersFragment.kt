package ch.comparis.challenge.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.comparis.challenge.databinding.FiltersBottomSheetBinding
import ch.comparis.challenge.model.CarsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FiltersFragment : BottomSheetDialogFragment() {

    private val viewModel: CarsViewModel by sharedViewModel()
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
                viewModel.incrementCount()
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