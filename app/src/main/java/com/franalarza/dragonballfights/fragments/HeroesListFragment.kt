package com.franalarza.dragonballfights.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.franalarza.dragonballfights.R
import com.franalarza.dragonballfights.activities.CallBackHeroFighters
import com.franalarza.dragonballfights.adapters.HeroesAdapter
import com.franalarza.dragonballfights.databinding.ActivityHeroesBinding
import com.franalarza.dragonballfights.databinding.FragmentHeroesListBinding
import com.franalarza.dragonballfights.models.HeroLive
import com.franalarza.dragonballfights.models.Heroe
import com.franalarza.dragonballfights.viewModels.HeroesActivityViewModel


class HeroesListFragment(private val heroes: MutableList<HeroLive>) : Fragment() {

    private lateinit var binding: FragmentHeroesListBinding
    private val viewModel: HeroesActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeroesListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecycler()
        context?.let { viewModel.setWinner(heroes, it) }
    }

    private fun onItemSelected(fighters: MutableList<HeroLive>) {
        var activity = activity as? CallBackHeroFighters
        activity?.passFighters(fighters)
    }

    private fun createRecycler() {
        val manager = LinearLayoutManager(context)
        val adapter = HeroesAdapter(heroes) {
            onItemSelected(it)
        }
        adapter.updateData(heroes)
        val decoration = DividerItemDecoration(context, manager.orientation)
        binding.rwHeroesList.adapter = adapter
        binding.rwHeroesList.layoutManager = manager
        binding.rwHeroesList.addItemDecoration(decoration)
    }


}