package com.example.retrofitpracticepokeapi.answerslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitpracticepokeapi.R
import com.example.retrofitpracticepokeapi.databinding.FragmentPokedexBinding
import kotlinx.coroutines.flow.collectLatest

class PokedexFragment : Fragment() {
    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: PokedexViewModel by lazy {
        ViewModelProvider(this).get(PokedexViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentPokedexBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_pokedex, container, false
        )

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        val adapter = PokemonGridAdapter()

        // ATTACHES ADAPTER TO RECYCLER VIEW
        binding.pokemonList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.pokemonListInfo.collectLatest {
                adapter.submitData(it)
            }
        }

        binding.pokemonList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.pokemonList.canScrollVertically(1)) {
                    viewModel.requestNewList()
                }
            }
        })
        return binding.root
    }
}
