package com.ashr.cleanMvvmAir.presentation.country

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashr.cleanMvvmAir.R
import com.ashr.cleanMvvmAir.databinding.FragmentCountryBinding
import com.ashr.cleanMvvmAir.presentation.MainActivity.Companion.BUNDLE_KEY
import com.ashr.cleanMvvmAir.presentation.MainActivity.Companion.REQUEST_KEY
import com.ashr.cleanMvvmAir.presentation.base.BaseFragment
import com.ashr.cleanMvvmAir.presentation.util.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryFragment : BaseFragment<FragmentCountryBinding>() {

    private val viewModel: CountryViewModel by viewModels()

    private lateinit var adapter: CountryAdapter


    override fun initUI() {
        activity?.supportFragmentManager?.setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to "Countries"))
        viewBinding.editTextFilter.afterTextChanged {
            viewModel.processIntents(CountryUiIntent.ChangeFilter(it))
        }
        viewBinding.recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = CountryAdapter() { countryName ->
            val bundle = Bundle().apply {
                putString("countryName", countryName)
            }
            findNavController().navigate(R.id.action_countryFragment_to_stateFragment, bundle)
        }
        viewBinding.recycler.adapter = adapter
    }

    override fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.isLoading) {
                        viewBinding.progressBar.visibility = View.VISIBLE
                        viewBinding.textInputFilter.visibility = View.GONE
                        viewBinding.recycler.visibility = View.GONE
                    } else {
                        viewBinding.progressBar.visibility = View.GONE
                        viewBinding.textInputFilter.visibility = View.VISIBLE
                        viewBinding.recycler.visibility = View.VISIBLE
                    }

                    if (uiState.fetchedCountries.isEmpty() && !uiState.isLoading && uiState.error.isEmpty()) {
                        viewModel.processIntents(CountryUiIntent.GetCountries)
                    }
                    adapter.submitList(uiState.filteredCountries)
                }
            }
        }
    }

    override fun getBinding(): FragmentCountryBinding =
        FragmentCountryBinding.inflate(layoutInflater)

}