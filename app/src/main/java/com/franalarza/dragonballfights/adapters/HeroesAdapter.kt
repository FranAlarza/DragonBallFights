package com.franalarza.dragonballfights.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.franalarza.dragonballfights.databinding.HeroItemBinding
import com.franalarza.dragonballfights.models.HeroLive
import com.franalarza.dragonballfights.models.Heroe

class HeroesAdapter(private val heroList: MutableList<HeroLive>, private val onHeroClicked: (MutableList<HeroLive>) -> Unit): RecyclerView.Adapter<HeroesAdapter.HeroesViewHolder>() {


    inner class HeroesViewHolder(private val binding: HeroItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, onHeroClicked: (MutableList<HeroLive>) -> Unit) {
            Glide.with(binding.heroImage.context).load(heroList[position].photo).into(binding.heroImage)
            binding.heroName.text = heroList[position].name
            if (!heroList[position].isAvailable) {
                !itemView.isClickable
            }

            itemView.setOnClickListener {
                val fighters = mutableListOf<HeroLive>()
                val heroSelected = heroList[position]
                val opponent = heroList.filter { it.name != heroSelected.name }.random()
                fighters.addAll(mutableListOf(heroSelected, opponent))
                onHeroClicked(fighters)
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

    fun updateData(newList: List<HeroLive>) {
        this.heroList.clear()
        this.heroList.addAll(newList)
        notifyDataSetChanged()
    }
}