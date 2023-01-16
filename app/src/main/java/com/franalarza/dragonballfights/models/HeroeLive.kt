package com.franalarza.dragonballfights.models

data class HeroLive(
    val id: String,
    val name: String,
    val description: String,
    val photo: String,
    var energy: Int,
    var isAvailable: Boolean
)