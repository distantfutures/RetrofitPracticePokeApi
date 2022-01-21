package com.example.retrofitpracticepokeapi

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofitpracticepokeapi.answerslist.PokemonGridAdapter
import com.example.retrofitpracticepokeapi.network.Pokemon

@BindingAdapter("listData")
fun bindRecyclerView(recycler: RecyclerView, data: List<Pokemon>?) {
    val adapter = recycler.adapter as PokemonGridAdapter
    adapter.submitList(data)
}
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}