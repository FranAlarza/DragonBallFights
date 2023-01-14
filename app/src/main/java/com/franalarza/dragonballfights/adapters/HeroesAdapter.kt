package com.franalarza.dragonballfights.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.franalarza.dragonballfights.databinding.HeroItemBinding
import com.franalarza.dragonballfights.models.HeroLive
import com.franalarza.dragonballfights.models.Heroe

class HeroesAdapter(private val heroList: MutableList<HeroLive>): RecyclerView.Adapter<HeroesAdapter.HeroesViewHolder>() {

    val list: List<HeroLive> = listOf(
        HeroLive("1", "Goku", "sdgvsdfg", "photo-1", 100),
        HeroLive("2", "Gotham", "sdgvsdfg", "photo-2", 100))

    inner class HeroesViewHolder(private val binding: HeroItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            Glide.with(binding.heroImage.context).load(heroList[position].photo).into(binding.heroImage)
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

    fun updateData(newList: List<HeroLive>) {
        this.heroList.clear()
        this.heroList.addAll(newList)
        notifyDataSetChanged()
    }
}