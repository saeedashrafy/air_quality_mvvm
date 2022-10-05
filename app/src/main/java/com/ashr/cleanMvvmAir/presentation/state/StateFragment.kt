package com.ashr.cleanMvvmAir.presentation.state

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
import com.ashr.cleanMvvmAir.databinding.FragmentStateBinding
import com.ashr.cleanMvvmAir.presentation.MainUiIntent
import com.ashr.cleanMvvmAir.presentation.MainViewModel
import com.ashr.cleanMvvmAir.presentation.base.BaseFragment
import com.ashr.cleanMvvmAir.presentation.util.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StateFragment : BaseFragment() {

    private var _binding: FragmentStateBinding? = null

    private val viewModel: StateViewModel by viewModels()
    private val sharedViewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: StateAdapter

    private var countryName: String = ""


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
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initUI() {
        sharedViewModel.processIntents(MainUiIntent.ChangeTitle("States of $countryName"))
        binding.editTextFilter.afterTextChanged {
            viewModel.processIntents(StateUiIntent.ChangeFilter(it))
        }
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = StateAdapter { stateName ->
            val bundle = Bundle().apply {
                putString("countryName", countryName)
                putString("stateName", stateName)
            }
            findNavController().navigate(R.id.action_stateFragment_to_cityFragment, bundle)
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
                    if (uiState.fetchedStates.isEmpty() && !uiState.isLoading && uiState.error.isEmpty()) {
                        viewModel.processIntents(StateUiIntent.GetStates(countryName))
                    }
                    adapter.submitList(uiState.filteredStates)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}