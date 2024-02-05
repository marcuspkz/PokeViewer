package com.example.pokeviewer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class PokemonAdapter(var pokemonList: List<PokemonDataResponse> = emptyList(), var detailedPokemonList: List<PokemonInfoResponse> = emptyList(),
                     ) : RecyclerView.Adapter<PokemonViewHolder>() {
    fun updateList(pkDataResponse: List<PokemonDataResponse>, pkSpritesResponse: List<PokemonInfoResponse>) {
        pokemonList = pkDataResponse
        detailedPokemonList = pkSpritesResponse
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        var sprite = detailedPokemonList[position].sprites.front_default
        var spriteShiny = detailedPokemonList[position].sprites.front_shiny
        var type0: String = detailedPokemonList[position].types[0].type.name
        var type1: String?
        var slash = "/"

        //Comprobación de que hay segundo tipo. Si no hay, se establece como vacío y no hay slash
        try {
            type1 = detailedPokemonList[position].types[1].type.name
        } catch (ex: IndexOutOfBoundsException) {
            type1 = null
        }
        if (type1 == null) {
            type1 = ""
            slash = ""
        }

        //Generamos la cadena con las estadísticas. Estas siempre vienen en orden desde la API,
        //por lo que podemos tratarlas con un sencillo array a través de sus primeras 6 componentes
        var stats: String = ("HP: " + detailedPokemonList[position].stats[0].stat + '\n'
                + "ATK: " + detailedPokemonList[position].stats[1].stat + '\n'
                + "DEF: " + detailedPokemonList[position].stats[2].stat + '\n'
                + "SPA: " + detailedPokemonList[position].stats[3].stat + '\n'
                + "SPD: " + detailedPokemonList[position].stats[4].stat + '\n'
                + "SPE: " + detailedPokemonList[position].stats[5].stat + '\n')

        //Algunos sprites pueden venir nulos desde la API. Evitamos el crasheo mediantes este if
        if (sprite != null && spriteShiny != null) {
            holder.bind( pokemonList[position].name.capitalized(), "#" + detailedPokemonList[position].id,
                "Type: " + type0.capitalized() + slash + type1.capitalized(), detailedPokemonList[position].sprites.front_default,
                detailedPokemonList[position].sprites.front_shiny, stats)
        } else {
            holder.bind(pokemonList[position].name.capitalized(), "#" + detailedPokemonList[position].id,
                "Type: " + type0.capitalized() + slash + type1.capitalized(), "???", "???",
                stats)
        }
    }

    override fun getItemCount() = pokemonList.size

    private fun String.capitalized(): String {
        return this.replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault())
            else it.toString()
        }
    }
}