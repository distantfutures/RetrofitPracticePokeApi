package com.example.retrofitpracticepokeapi.answerslist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofitpracticepokeapi.R
import com.example.retrofitpracticepokeapi.databinding.PokemonGridItemViewBinding
import com.example.retrofitpracticepokeapi.model.Pokemon

const val GAT = "CheckAdapter"
class PokemonGridAdapter() : PagingDataAdapter<Pokemon, PokemonGridAdapter.PokemonViewHolder>(DiffCallback) {
    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val pokemonName: TextView = view.findViewById(R.id.pokemon_name)
        private val pokemonUrl: ImageView = view.findViewById(R.id.pokemon_image_url)
        private var pokemon: Pokemon? = null
        fun bind (pokemon: Pokemon) {
            this.pokemon = pokemon
            pokemonName.text = pokemon.name
            Log.i(GAT, "Pokemon ADAPTER: ${pokemon.name}")

            val imgUrl = pokemon.sprites.newUrl
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(pokemonUrl.context)
                .load(imgUri)
                .into(pokemonUrl)

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
        return PokemonViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_grid_item_view, parent,false))
    }

    override fun onBindViewHolder(
        holder: PokemonViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }
}
