package com.example.weatherforecast.screen

import com.example.weatherforecast.R
import com.example.weatherforecast.presentation.MainActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton

object SearchScreen: KScreen<SearchScreen>() {
    override val layoutId: Int = R.layout.fragment_search
    override val viewClass: Class<*> = MainActivity::class.java

    val searchBtn = KButton {withId(R.id.searchBtn)}
    val textInput = KEditText {withId(R.id.input)}

}