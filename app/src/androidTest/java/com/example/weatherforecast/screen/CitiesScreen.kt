package com.example.weatherforecast.screen

import com.example.weatherforecast.R
import com.example.weatherforecast.presentation.MainActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton

object CitiesScreen : KScreen<CitiesScreen>() {

    override val layoutId: Int = R.layout.fragment_places
    override val viewClass: Class<*> = MainActivity::class.java

    val clearBtn = KButton{ withId(R.id.clearAllBtn) }
    val savedPlaces = KRecyclerView(builder = {withId(R.id.recyclerView)}, {})
}