package com.example.weatherforecast.screen

import com.example.weatherforecast.R
import com.example.weatherforecast.presentation.MainActivity

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KTextView

object DetailsScreen : KScreen<DetailsScreen>() {

    override val layoutId: Int = R.layout.fragment_weather_details
    override val viewClass: Class<*> = MainActivity::class.java

    val placeName = KTextView {withId(R.id.name)}

}