package com.franalarza.dragonballfights.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.franalarza.dragonballfights.databinding.HeroItemBinding
import com.franalarza.dragonballfights.models.Heroe

class HeroesAdapter(val heroList: List<Heroe>): RecyclerView.Adapter<HeroesAdapter.HeroesViewHolder>() {

    inner class HeroesViewHolder(private val binding: HeroItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.heroName.text = heroList[position].name
        }
    }

    override fun getItemCount(): Int {
        return heroList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesViewHolder {
        return HeroesViewHolder(
            HeroItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HeroesViewHolder, position: Int) {
        holder.bind(position)
    }
}