package com.example.pokeviewer

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    @SerializedName("results") val results: List<PokemonDataResponse>
)
data class PokemonDataResponse (
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

