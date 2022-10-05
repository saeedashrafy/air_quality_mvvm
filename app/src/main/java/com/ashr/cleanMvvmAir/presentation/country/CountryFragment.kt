package com.ashr.cleanMvvmAir.presentation.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashr.cleanMvvmAir.R
import com.ashr.cleanMvvmAir.databinding.FragmentCountryBinding
import com.ashr.cleanMvvmAir.presentation.MainUiIntent
import com.ashr.cleanMvvmAir.presentation.MainViewModel
import com.ashr.cleanMvvmAir.presentation.base.BaseFragment
import com.ashr.cleanMvvmAir.presentation.util.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryFragment : BaseFragment() {

    private var _binding: FragmentCountryBinding? = null

    private val viewModel: CountryViewModel by viewModels()
    private val sharedViewModel: MainViewModel by activityViewModels()


    private lateinit var adapter: CountryAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initUI() {
        sharedViewModel.processIntents(MainUiIntent.ChangeTitle("Countries"))
        binding.editTextFilter.afterTextChanged {
            viewModel.processIntents(CountryUiIntent.ChangeFilter(it))
        }
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = CountryAdapter() { countryName ->
            val bundle = Bundle().apply {
                putString("countryName", countryName)
            }
            findNavController().navigate(R.id.action_countryFragment_to_stateFragment, bundle)
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
                        binding.textInputFilter.visibility = View.VISIBLE
                        binding.recycler.visibility = View.VISIBLE
                    }

                    if (uiState.fetchedCountries.isEmpty() && !uiState.isLoading && uiState.error.isEmpty()) {
                        viewModel.processIntents(CountryUiIntent.GetCountries)
                    }
                    adapter.submitList(uiState.filteredCountries)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}