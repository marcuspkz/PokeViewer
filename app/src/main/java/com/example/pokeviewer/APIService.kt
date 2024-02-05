package com.example.pokeviewer

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("pokemon/?limit=1302")
    suspend fun getAllPokemon(): Response<PokemonListResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") pokemonName: String): Response<PokemonInfoResponse>
}