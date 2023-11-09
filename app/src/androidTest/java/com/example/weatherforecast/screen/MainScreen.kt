package com.example.weatherforecast.screen

import com.example.weatherforecast.R
import com.example.weatherforecast.presentation.MainActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton

object MainScreen : KScreen<MainScreen>() {

    override val layoutId: Int = R.layout.activity_main
    override val viewClass: Class<*> = MainActivity::class.java

    val citiesLayoutBtn = KButton {withId(R.id.citiesBtn)}
    val searchLayoutBtn = KButton {withId(R.id.searchLayoutBtn)}
}