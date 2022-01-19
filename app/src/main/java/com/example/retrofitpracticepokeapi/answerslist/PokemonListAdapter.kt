package com.example.retrofitpracticepokeapi.answerslist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitpracticepokeapi.R
import com.example.retrofitpracticepokeapi.network.Pokemon
import com.example.retrofitpracticepokeapi.network.PokemonList
import java.util.*

class PokemonListAdapter(pokemon: PokemonList) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    // Create a variable that holds a list of data
    var pokeList = pokemon
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = pokeList.results.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = pokeList.results[position]
        holder.bind(item)
        Log.i("ResponseAdapter", "pokeList: ${pokeList.results.size}")
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)
        val pokemonImageUrl: TextView = itemView.findViewById(R.id.pokemon_image_url)

        fun bind(
            item: Pokemon
        ) {
            pokemonName.text = item.name.capitalizeWords()
            pokemonImageUrl.text = item.url
        }
        fun String.capitalizeWords(): String = split(" ").joinToString(" ") {
            it.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.pokemon_list_item_view, parent, false)
                return ViewHolder(view)
            }
        }
    }
}
