package com.franalarza.dragonballfights.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.franalarza.dragonballfights.databinding.HeroItemBinding
import com.franalarza.dragonballfights.models.HeroLive
import com.franalarza.dragonballfights.models.Heroe

class HeroesAdapter(
    private var heroList: MutableList<HeroLive>,
    private val onHeroClicked: (MutableList<HeroLive>) -> Unit
) : RecyclerView.Adapter<HeroesAdapter.HeroesViewHolder>() {

    inner class HeroesViewHolder(private val binding: HeroItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, onHeroClicked: (MutableList<HeroLive>) -> Unit) {
            val hero = heroList[position]

            Glide.with(binding.heroImage.context).load(hero.photo)
                .into(binding.heroImage)
            binding.heroName.text = hero.name

            if (hero.energy <= 0) {
                binding.notAvailableMarker.visibility = View.VISIBLE
                itemView.isEnabled = false
            } else {
                binding.notAvailableMarker.visibility = View.GONE
            }

            if (hero.energy > 0) {
                itemView.setOnClickListener {
                    val fighters = mutableListOf<HeroLive>()
                    val heroSelected = heroList[position]
                    val opponent = heroList.filter { it.name != heroSelected.name && it.energy > 0 }.random()
                    fighters.addAll(mutableListOf(heroSelected, opponent))
                    onHeroClicked(fighters)
                }
            } else {
                return
            }
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
        holder.bind(position, onHeroClicked)
    }

    fun updateData(newList: MutableList<HeroLive>) {
        heroList = newList
        notifyDataSetChanged()
    }

}