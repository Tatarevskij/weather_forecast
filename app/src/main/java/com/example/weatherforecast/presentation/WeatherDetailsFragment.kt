package com.example.weatherforecast.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentWeatherDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

@AndroidEntryPoint
class WeatherDetailsFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentWeatherDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainActivity: WeakReference<MainActivity> = WeakReference(requireActivity() as MainActivity)
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope
            .launch {
                viewModel.placeFlow.collect {
                    if (it != null) {
                        binding.name.text = it.location.name
                        binding.date.text = it.location.localTime
                        binding.temp.text = it.current.temp
                    }
                }
            }

        mainActivity.get()!!.binding.citiesBtn.isEnabled = true
        mainActivity.get()!!.binding.searchLayoutBtn.isEnabled = true
        mainActivity.get()!!.binding.searchLayoutBtn.setOnClickListener {
            findNavController().navigate(R.id.action_weatherDetailsFragment_to_searchFragment)
        }
        mainActivity.get()!!.binding.citiesBtn.setOnClickListener {
            findNavController().navigate(R.id.action_weatherDetailsFragment_to_placesFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}