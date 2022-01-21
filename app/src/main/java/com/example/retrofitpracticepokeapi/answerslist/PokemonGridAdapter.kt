package com.example.retrofitpracticepokeapi.answerslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitpracticepokeapi.databinding.PokemonGridItemViewBinding
import com.example.retrofitpracticepokeapi.network.Pokemon

class PokemonGridAdapter() : ListAdapter<Pokemon, PokemonGridAdapter.PokemonViewHolder>(DiffCallback) {
    class PokemonViewHolder(private var binding: PokemonGridItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (item: Pokemon) {
            binding.pokemonList = item
            binding.executePendingBindings()
        }
    }
    companion object DiffCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(PokemonGridItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(
        holder: PokemonViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }
}
