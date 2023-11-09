package com.example.weatherforecast.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentPlacesBinding
import com.example.weatherforecast.entity.PlaceInRepo
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class PlacesFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentPlacesBinding? = null
    private val binding get() = _binding!!
    private var placesAdapter = PlacesAdapter { place -> onItemClick(place)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlacesBinding.inflate(inflater, container, false)
        binding.recyclerView.adapter = placesAdapter

        binding.clearAllBtn.setOnClickListener {
            viewModel.clearDb()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainActivity = WeakReference(requireActivity() as MainActivity)
        viewModel.getAllPlacesFromDb(placesAdapter)

        mainActivity.get()!!.binding.citiesBtn.isEnabled = false
        mainActivity.get()!!.binding.searchLayoutBtn.isEnabled = true
        mainActivity.get()!!.binding.searchLayoutBtn.setOnClickListener {
            findNavController().navigate(R.id.action_citiesFragment_to_searchFragment)
        }
    }

    private fun onItemClick(item: PlaceInRepo){
        viewModel.getPlace(item.name)
        findNavController().navigate(R.id.action_placesFragment_to_weatherDetailsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.adapterJob.cancel()
    }
}