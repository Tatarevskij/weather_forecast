package com.example.weatherforecast.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.errorFlow()
        binding.searchLayoutBtn.isEnabled = false

        binding.citiesBtn.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_searchFragment_to_citiesFragment)
            binding.citiesBtn.isEnabled = false
            binding.searchLayoutBtn.isEnabled = true
        }

        binding.searchLayoutBtn.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_citiesFragment_to_searchFragment)
            binding.citiesBtn.isEnabled = true
            binding.searchLayoutBtn.isEnabled = false
        }


        lifecycleScope.launch {
            viewModel.errorFlow.collect{
                if (it != null) {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}