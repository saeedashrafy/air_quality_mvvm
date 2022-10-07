package com.ashr.cleanMvvmAir.presentation.city.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashr.cleanMvvmAir.R
import com.ashr.cleanMvvmAir.databinding.FragmentStateBinding
import com.ashr.cleanMvvmAir.presentation.MainUiIntent
import com.ashr.cleanMvvmAir.presentation.MainViewModel
import com.ashr.cleanMvvmAir.presentation.base.BaseFragment
import com.ashr.cleanMvvmAir.presentation.util.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CityFragment : BaseFragment<FragmentStateBinding>() {

    private val viewModel: CityViewModel by viewModels()
    private val sharedViewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: CityAdapter

    private var countryName: String = ""
    private var stateName: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            countryName = it.getString("countryName").toString()
            stateName = it.getString("stateName").toString()
        }
        super.onViewCreated(view, savedInstanceState)

    }

    override fun initUI() {
        sharedViewModel.processIntents(MainUiIntent.ChangeTitle("Cities of $stateName"))
        viewBinding.editTextFilter.afterTextChanged {
            viewModel.processIntents(CityUiIntent.ChangeFilter(it))
        }
        viewBinding.recycler.layoutManager = LinearLayoutManager(requireContext())
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
                        if (uiState.error.isEmpty()) {
                            viewBinding.textInputFilter.visibility = View.VISIBLE
                            viewBinding.recycler.visibility = View.VISIBLE
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


    override fun getBinding(): FragmentStateBinding = FragmentStateBinding.inflate(layoutInflater)
}