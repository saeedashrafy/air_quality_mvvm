package com.ashr.cleanMvvmAir.presentation.city.data

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ashr.cleanMvvmAir.R
import com.ashr.cleanMvvmAir.databinding.FragmentCityDataBinding
import com.ashr.cleanMvvmAir.domain.model.AqiLevel
import com.ashr.cleanMvvmAir.presentation.MainActivity
import com.ashr.cleanMvvmAir.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CityDataFragment : BaseFragment<FragmentCityDataBinding>() {


    private val viewModel: CityDataViewModel by viewModels()

    private var countryName: String = ""
    private var stateName: String = ""
    private var cityName: String = ""

    lateinit var shape: GradientDrawable



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getData()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun getData() {
        arguments?.let {
            countryName = it.getString("countryName").toString()
            stateName = it.getString("stateName").toString()
            cityName = it.getString("cityName").toString()
        }
    }

    override fun initUI() {
        activity?.supportFragmentManager?.setFragmentResult(
            MainActivity.REQUEST_KEY, bundleOf(
                MainActivity.BUNDLE_KEY to "AQI of $cityName from $countryName")
        )
        shape = GradientDrawable()
        shape.cornerRadius = 15f
    }

    override fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.isLoading) {
                        viewBinding.progressBar.visibility = View.VISIBLE
                        viewBinding.containerData.visibility = View.GONE
                        viewBinding.containerUpdated.visibility = View.GONE
                        viewBinding.containerTemperature.visibility = View.GONE
                        viewBinding.containerHumidity.visibility = View.GONE
                    } else {
                        viewBinding.progressBar.visibility = View.GONE
                        viewBinding.containerData.visibility = View.VISIBLE
                        viewBinding.containerUpdated.visibility = View.VISIBLE
                        viewBinding.containerTemperature.visibility = View.VISIBLE
                        viewBinding.containerHumidity.visibility = View.VISIBLE
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
                            viewBinding.textviewIndex.text = it
                        }
                        viewBinding.textviewLevel.text = cityData.domainPollutionData.aqiLevel.levelName
                        viewBinding.textviewTemperatureValue.text =
                            cityData.domainWeatherData.temperature.toString()
                        viewBinding.textviewUpdatedValue.text =
                            cityData.domainPollutionData.updatedTime
                        viewBinding.textviewHumidityValue.text =
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

    override fun getBinding(): FragmentCityDataBinding =
        FragmentCityDataBinding.inflate(layoutInflater)

}