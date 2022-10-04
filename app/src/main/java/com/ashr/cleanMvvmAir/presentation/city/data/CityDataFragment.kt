package com.ashr.cleanMvvmAir.presentation.city.data

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ashr.cleanMvvmAir.R
import com.ashr.cleanMvvmAir.databinding.FragmentCityDataBinding
import com.ashr.cleanMvvmAir.domain.model.AqiLevel
import com.ashr.cleanMvvmAir.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CityDataFragment : BaseFragment() {

    private var _binding: FragmentCityDataBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CityDataViewModel by viewModels()

    private var countryName: String = ""
    private var stateName: String = ""
    private var cityName: String = ""

    lateinit var shape: GradientDrawable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData() {
        arguments?.let {
            countryName = it.getString("countryName").toString()
            stateName = it.getString("stateName").toString()
            cityName = it.getString("cityName").toString()
        }
    }

    override fun initUI() {
        shape = GradientDrawable()
        shape.cornerRadius = 15f
    }

    override fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.containerData.visibility = View.GONE
                        binding.containerTemperature.visibility = View.GONE
                        binding.containerHumidity.visibility = View.GONE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.containerData.visibility = View.VISIBLE
                        binding.containerTemperature.visibility = View.VISIBLE
                        binding.containerHumidity.visibility = View.VISIBLE
                    }
                    if (uiState.data == null && !uiState.isLoading && uiState.error.isEmpty()) {
                        viewModel.processIntents(
                            CityDataUiIntent.GetData(
                                countryName,
                                stateName,
                                cityName
                            )
                        )
                    }
                    uiState.data?.let { cityData ->
                        "US AQI ${cityData.domainPollutionData.airQualityIndexUs}".let {
                            binding.textviewIndex.text = it
                        }
                        binding.textviewLevel.text = cityData.domainPollutionData.aqiLevel.levelName
                        binding.textviewTemperatureValue.text =
                            cityData.domainWeatherData.temperature.toString()
                        binding.textviewUpdatedValue.text =
                            cityData.domainPollutionData.updatedTime
                        binding.textviewHumidityValue.text =
                            cityData.domainWeatherData.humidity
                    }
                }
            }
        }
    }

    fun AqiLevel.getAppropriateColor(): Int {
        return when (this) {
            AqiLevel.Good -> R.color.aqi_good
            AqiLevel.Moderate -> R.color.aqi_moderate
            AqiLevel.UnhealthyForSensitiveGroups -> R.color.aqi_unhealthyForSensitive
            AqiLevel.Unhealthy -> R.color.aqi_unhealthy
            AqiLevel.VeryUnhealthy -> R.color.aqi_veryUnhealthy
            AqiLevel.Hazardous -> R.color.aqi_hazardous
            AqiLevel.Unknown -> R.color.aqi_good
        }
    }

    fun AqiLevel.getAppropriateIcon(): String {
        return when (this) {
            AqiLevel.Good -> "https://img.icons8.com/emoji/48/000000/smiling-face-with-smiling-eyes.png"
            AqiLevel.Moderate -> "https://img.icons8.com/emoji/48/000000/slightly-smiling-face.png"
            AqiLevel.UnhealthyForSensitiveGroups -> "https://img.icons8.com/emoji/48/000000/pensive-face.png"
            AqiLevel.Unhealthy -> "https://img.icons8.com/emoji/48/000000/sad-but-relieved-face.png"
            AqiLevel.VeryUnhealthy -> "https://img.icons8.com/emoji/48/000000/face-with-medical-mask.png"
            AqiLevel.Hazardous -> "https://img.icons8.com/emoji/48/000000/face-vomiting.png"
            AqiLevel.Unknown -> "https://img.icons8.com/emoji/48/000000/smiling-face-with-smiling-eyes.png"
        }
    }
}