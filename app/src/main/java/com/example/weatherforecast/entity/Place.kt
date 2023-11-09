package com.example.weatherforecast.entity

import java.util.*

interface Place {
    val location: Location
    val current: Current
}

interface Location {
    val name: String
    val localTime: String
}

interface Current{
    val temp: String
}

class TestPlace(
    private val name: String,
    override val location: Location = TestLocation(name),
    override val current: Current = TestCurrent()
) : Place {
    class TestLocation(
        private val newName: String,
        override val name: String = newName,
        override val localTime: String = Calendar.DATE.toString()
    ) : Location

    class TestCurrent(
        override val temp: String = "100"
    ) : Current
}


