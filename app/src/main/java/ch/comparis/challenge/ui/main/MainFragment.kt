package ch.comparis.challenge.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ch.comparis.challenge.adapter.CarsAdapter
import ch.comparis.challenge.databinding.FragmentMainBinding
import ch.comparis.challenge.model.CarsViewModel
import ch.comparis.challenge.model.FiltersViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {
    private val carsViewModel: CarsViewModel by sharedViewModel()
    private val filtersViewModel: FiltersViewModel by sharedViewModel()
    private var binding: FragmentMainBinding? = null
    private val carsAdapter = CarsAdapter{
        when {
            it.isFavorite -> carsViewModel.addCarToFavorite(it)
            else -> carsViewModel.removeCarFromFavorite(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentMainBinding.inflate(layoutInflater, container, false)
        binding = fragmentBinding
        setupCarsRecycleView()
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            firstButton.setOnClickListener { openFiltersFragment() }
        }
    }

    private fun openFiltersFragment() {
        val filtersBottomSheetDialogFragment = FiltersFragment.newInstance()
        filtersBottomSheetDialogFragment.show(requireActivity().supportFragmentManager, FiltersFragment.TAG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        carsViewModel.cars.observe(this) {
            carsAdapter.updateCars(it)
        }
        filtersViewModel.filter.observe(this) {
            carsViewModel.filterByMakes(it)
        }
    }

    private fun setupCarsRecycleView() {
        binding?.apply {
            with(carsRecycleView) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = carsAdapter
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}