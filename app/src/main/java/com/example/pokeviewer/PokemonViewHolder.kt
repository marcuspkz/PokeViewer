package com.example.pokeviewer

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeviewer.databinding.PokemonCardBinding
import com.squareup.picasso.Picasso

class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = PokemonCardBinding.bind(view)

    fun bind(pokemonName: String, pokemonID: String, pokemonTypes: String, pokemonSprite: String,
             pokemonShinySprite: String, pokemonStats: String) {
        binding.tvPokemonName.text = pokemonName
        binding.tvPokemonID.text = pokemonID
        binding.tvPokemonTypes.text = pokemonTypes
        binding.stats.text = pokemonStats
        Picasso.get().load(pokemonSprite).into(binding.ivPokemon)
        Picasso.get().load(pokemonShinySprite).into(binding.ivPokemonShiny)
        Picasso.get().load("https://images6.alphacoders.com/613/thumb-1920-613932.png").into(binding.backgroundImage)
    }
}
