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
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {
    private val viewModel: CarsViewModel by sharedViewModel()
    private var binding: FragmentMainBinding? = null

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
        FiltersFragment.newInstance().show(requireActivity().supportFragmentManager, FiltersFragment.TAG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.count.observe(this) {
            Toast.makeText(requireContext(), "Count $it", Toast.LENGTH_SHORT).show()
        }
        viewModel.cars.observe(this) {
            Toast.makeText(requireContext(), "Count ${it[0].make}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setupCarsRecycleView() {
        val carsAdapter = CarsAdapter()
        binding!!.carsRecycleView.let { recyclerView ->
                with(recyclerView) {
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