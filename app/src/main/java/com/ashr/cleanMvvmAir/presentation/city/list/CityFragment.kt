package com.ashr.cleanMvvmAir.presentation.city.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashr.cleanMvvmAir.R
import com.ashr.cleanMvvmAir.databinding.FragmentStateBinding
import com.ashr.cleanMvvmAir.presentation.base.BaseFragment
import com.ashr.cleanMvvmAir.presentation.util.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CityFragment : BaseFragment() {

    private var _binding: FragmentStateBinding? = null

    private val viewModel: CityViewModel by viewModels()
    private lateinit var adapter: CityAdapter

    private var countryName: String = ""
    private var stateName: String = ""


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStateBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            countryName = it.getString("countryName").toString()
            stateName = it.getString("stateName").toString()
        }
        super.onViewCreated(view, savedInstanceState)

    }

    override fun initUI() {
        binding.editTextFilter.afterTextChanged {
            viewModel.processIntents(CityUiIntent.ChangeFilter(it))
        }
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = CityAdapter { cityName ->
            val bundle = Bundle().apply {
                putString("countryName", countryName)
                putString("stateName", stateName)
                putString("cityName", cityName)
            }
            findNavController().navigate(
                R.id.action_cityFragment_to_cityDataFragment,
                args = bundle
            )
        }
        binding.recycler.adapter = adapter
    }

    override fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.textInputFilter.visibility = View.GONE
                        binding.recycler.visibility = View.GONE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        if (uiState.error.isEmpty()) {
                            binding.textInputFilter.visibility = View.VISIBLE
                            binding.recycler.visibility = View.VISIBLE
                        }
                    }
                    if (uiState.fetchedCities.isEmpty() && !uiState.isLoading && uiState.error.isEmpty()) {
                        viewModel.processIntents(CityUiIntent.GetCities(countryName, stateName))
                    }
                    adapter.submitList(uiState.filteredCities)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}