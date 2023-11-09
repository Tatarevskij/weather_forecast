package com.example.weatherforecast.presentation


import android.content.SharedPreferences
import androidx.lifecycle.*
import com.example.weatherforecast.domain.*
import com.example.weatherforecast.entity.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

const val KEY_STRING = "key_string"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPlaceUseCase: GetPlaceUseCase,
    private val addPlaceToDbUseCase: AddPlaceToDbUseCase,
    private val getAllPlacesFromDbUseCase: GetAllPlacesFromDbUseCase,
    private val updatePlaceInDbUseCase: UpdatePlaceInDbUseCase,
    private val clearDbUseCase: ClearDbUseCase,
    private val repositoryStatusCollectingUseCase: RepositoryStatusCollectingUseCase,
    val searchedNamesPrefs: SharedPreferences
) : ViewModel() {
    private val editor: SharedPreferences.Editor = searchedNamesPrefs.edit()

    var adapterJob: Job = Job()

    val query = MutableLiveData(String())
    val results: LiveData<List<String>> = query
        .distinctUntilChanged()
        .map { search(it) }
    private val searchedPlaces =
        searchedNamesPrefs.getString(KEY_STRING, "")!!.split(",").toList()

    fun search(query: String): List<String> {
        return searchedPlaces.filter { it.contains(query, ignoreCase = true) }
    }

    private val _placeFlow = MutableStateFlow<Place?>(null)
    val placeFlow = _placeFlow.asStateFlow()

    private val _errorFlow = MutableStateFlow<String?>(null)
    val errorFlow = _errorFlow.asStateFlow()

    fun errorFlow() {
        viewModelScope.launch {
            _errorFlow.emitAll(repositoryStatusCollectingUseCase.execute())
        }
    }

    fun setToSharedPrefs(name: String) {
        var currentPrefsText = searchedNamesPrefs.getString(KEY_STRING, "")
        if (!currentPrefsText!!.contains(name, ignoreCase = true)) {
            currentPrefsText += ",$name"
            editor
                .putString(KEY_STRING, currentPrefsText)
                .apply()
        }
    }

    fun getPlace(name: String) {
        viewModelScope.launch {
            val place = getPlaceUseCase.execute(name)
            _placeFlow.value = place
            if (place != null) insertOrUpdate(place)
            cancel()
        }
    }

    fun getAllPlacesFromDb(placesAdapter: PlacesAdapter) {
        adapterJob = viewModelScope.launch {
            getAllPlacesFromDbUseCase.execute().collect {
                placesAdapter.submitList(it)
            }
        }
    }

    private fun insertOrUpdate(newPlace: Place) {
        viewModelScope.launch {
            getAllPlacesFromDbUseCase.execute().collect {
                if (it.isEmpty()) {
                    addPlaceToDbUseCase.execute(newPlace)
                    cancel()
                    return@collect
                }
                it.forEach { place ->
                    if (place.name == newPlace.location.name) {
                        updatePlaceInDbUseCase.execute(place.id, newPlace)
                        cancel()
                        return@collect
                    }
                }
                addPlaceToDbUseCase.execute(newPlace)
                cancel()
                return@collect
            }
        }
    }

    fun clearDb() {
        viewModelScope.launch(Dispatchers.IO) {
            clearDbUseCase.execute()
            cancel()
        }
    }
}
