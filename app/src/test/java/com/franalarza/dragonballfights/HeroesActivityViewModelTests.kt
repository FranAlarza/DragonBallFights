package com.franalarza.dragonballfights

import com.franalarza.dragonballfights.models.HeroLive
import com.franalarza.dragonballfights.viewModels.HeroesActivityViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HeroesActivityViewModelTests {

    private lateinit var mockList: MutableList<HeroLive>
    private val sut = HeroesActivityViewModel()

    @Before fun prepareTest() {
        mockList = mutableListOf(
            HeroLive("1", "Goku", "", "photo-1", 100, true),
            HeroLive("2", "Trunks", "", "photo-2", 0, true)
        )
    }

    @Test
    fun getHeroesSuccess() {
        // Given
        sut.heroesList = mockList
        // When
        val list = sut.getHeroes()
        // Then
        Assert.assertTrue(list.size == 2)
    }

    @Test
    fun setWinnerSuccess() {
        // Given
        val fighters = mockList
        var message = ""

        // When
        sut.setWinner(fighters) {
            message = it
        }

        //Then
        Assert.assertTrue(message.isNotEmpty())

    }

    @Test
    fun restartGameSuccess() {
        // Given
        val fighters = mockList

        // When
        sut.restartGame(fighters)

        //Then
        Assert.assertTrue(fighters[0].energy == 100 && fighters[1].energy == 100)

    }
}