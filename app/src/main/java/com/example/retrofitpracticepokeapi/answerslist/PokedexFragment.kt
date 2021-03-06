package com.example.retrofitpracticepokeapi.answerslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.retrofitpracticepokeapi.Injection
import com.example.retrofitpracticepokeapi.R
import com.example.retrofitpracticepokeapi.databinding.FragmentPokedexBinding
import kotlinx.coroutines.flow.collectLatest

class PokedexFragment : Fragment() {
    /**
     * Lazily initialize our [OverviewViewModel].
     */
//    private val viewModel: PokedexViewModel by lazy {
//        ViewModelProvider(this).get(PokedexViewModel::class.java)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
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

        val viewModel = ViewModelProvider(
            this, Injection.provideViewModelFactory(
                context = requireContext()
            )
        ).get(PokedexViewModel::class.java)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        val adapter = PokemonGridAdapter()

        // ATTACHES ADAPTER TO RECYCLER VIEW
        binding.pokemonList.adapter = adapter
        binding.pokemonList.adapter = adapter.withLoadStateFooter(
            footer = LoadStateAdapter {adapter.retry()}
        )

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.pokemonListInfo.collectLatest {
                adapter.submitData(it)
            }
        }
        return binding.root
    }
}
