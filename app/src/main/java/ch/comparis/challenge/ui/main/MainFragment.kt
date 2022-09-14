package ch.comparis.challenge.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import ch.comparis.challenge.adapter.CarsAdapter
import ch.comparis.challenge.databinding.FragmentMainBinding
import ch.comparis.challenge.model.CarsViewModel
import ch.comparis.challenge.model.FiltersViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {
    private val carsViewModel: CarsViewModel by sharedViewModel()
    private val filtersViewModel: FiltersViewModel by sharedViewModel()
    private lateinit var binding: FragmentMainBinding
    private val carsAdapter = CarsAdapter {
        when (it.isFavorite) {
            true -> carsViewModel.addCarToFavorite(it)
            false -> carsViewModel.removeCarFromFavorite(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            with(carsRecycleView) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = carsAdapter
            }
            filterButton.setOnClickListener { openFiltersFragment() }
        }
    }

    private fun openFiltersFragment() {
        val filtersBottomSheetDialogFragment = FiltersFragment.newInstance()
        filtersBottomSheetDialogFragment.show(requireActivity().supportFragmentManager, FiltersFragment.TAG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        carsViewModel.cars.observe(requireActivity()) {
            binding.emptyStateTextView.isVisible = it.isEmpty()
            binding.carsRecycleView.isVisible = it.isNotEmpty()
            carsAdapter.updateCars(it)
        }
        filtersViewModel.filter.observe(this) {
            carsViewModel.filterCars(it)
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}