package com.example.weatherforecast.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainActivity: WeakReference<MainActivity> = WeakReference(requireActivity() as MainActivity)
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_list_item_1,
            viewModel.searchedNamesPrefs.getString(KEY_STRING, "")!!.split(",").toTypedArray()
        )
        binding.input.setAdapter(adapter)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.input.addTextChangedListener {
            println( viewModel.search(it.toString()))
            binding.output.text = SearchViewUtils.resultsString(viewModel.search(it.toString()))
        }

        binding.searchBtn.setOnClickListener {
            val inputText = binding.input.text.toString()
            viewModel.getPlace(inputText)
            viewModel.setToSharedPrefs(inputText)
            findNavController().navigate(R.id.action_searchFragment_to_weatherDetailsFragment)
        }

        mainActivity.get()!!.binding.citiesBtn.isEnabled = true
        mainActivity.get()!!.binding.searchLayoutBtn.isEnabled = false
        mainActivity.get()!!.binding.citiesBtn.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_citiesFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
