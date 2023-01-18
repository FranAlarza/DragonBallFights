package com.franalarza.dragonballfights.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.franalarza.dragonballfights.databinding.FragmentBattleBinding
import com.franalarza.dragonballfights.models.HeroLive
import com.franalarza.dragonballfights.utils.Constants
import com.franalarza.dragonballfights.viewModels.HeroesActivityViewModel

class BattleFragment(private val fighters: MutableList<HeroLive>) : Fragment() {


    private lateinit var binding: FragmentBattleBinding
    private val viewModel: HeroesActivityViewModel by viewModels()
    private var round = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBattleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (fighters.size > 0) {
            setDataFighters()
        }
        setListeners()
        viewModel.checkIfFightersAreReady(fighters) {
            binding.fabRound.isEnabled = false
        }
    }

    private fun setListeners() {
        binding.fabClear.setOnClickListener {
            binding.fabRound.isEnabled = true
            viewModel.restartBattle(fighters, binding.pbFighter1, binding.pbFighter2)
            binding.fabRound.text = "Round 1"
            round = 1
        }

        binding.fabRound.setOnClickListener {

            val pbFirstFighter = binding.pbFighter1
            val pbSecondFighter = binding.pbFighter2
            viewModel.executeRound(fighters, pbFirstFighter, pbSecondFighter) {

                if (fighters[Constants.firstFighter].energy <= 0 || fighters[Constants.secondFighter].energy <= 0) {
                    val winner = fighters.first { it.energy > 0 }
                    Toast.makeText(context, "The winner of the Battle is ${winner.name}", Toast.LENGTH_LONG).show()
                    binding.fabRound.isEnabled = false
                    round = 0
                }
            }
            round++
            binding.fabRound.text = "Round $round"
        }

    }

    private fun setDataFighters() {
        if (fighters.size == 1) {
            Glide.with(binding.ivFighter1.context).load(fighters[Constants.firstFighter].photo).into(binding.ivFighter1)
            binding.tvNameFighter1.text = fighters[Constants.firstFighter].name
            binding.pbFighter1.progress = fighters[Constants.firstFighter].energy
        } else {
            Glide.with(binding.ivFighter1.context).load(fighters[Constants.firstFighter].photo).into(binding.ivFighter1)
            binding.tvNameFighter1.text = fighters[Constants.firstFighter].name
            binding.pbFighter1.progress = fighters[Constants.firstFighter].energy

            Glide.with(binding.ivFighter2.context).load(fighters[Constants.secondFighter].photo).into(binding.ivFighter2)
            binding.tvNameFighter2.text = fighters[Constants.secondFighter].name
            binding.pbFighter2.progress = fighters[Constants.secondFighter].energy
        }

    }

}