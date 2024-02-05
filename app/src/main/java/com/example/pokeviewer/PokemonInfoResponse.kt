package com.example.pokeviewer

import com.google.gson.annotations.SerializedName

data class PokemonInfoResponse(
    @SerializedName("id") val id: String,
    @SerializedName("sprites") val sprites: PokemonImageResponse,
    @SerializedName("types") val types: List<PokemonTypesResponse>,
    @SerializedName("stats") val stats: List<PokemonStatsResponse>,
)

data class PokemonImageResponse(
    @SerializedName("front_default") val front_default: String,
    @SerializedName("front_shiny") val front_shiny: String
)

data class PokemonTypesResponse(
    @SerializedName("slot") val slot: String,
    @SerializedName("type") val type: TypeResponse
)

data class TypeResponse(
    @SerializedName("name") val name: String,
)

data class PokemonStatsResponse(
    @SerializedName("base_stat") val stat: String
)