package com.example.pokeviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.pokeviewer.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonListActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit

    private lateinit var adapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI(){
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }
            override fun onQueryTextChange(newText: String?) = false
        })
        adapter = PokemonAdapter()
        binding.rvPokemon.setHasFixedSize(true)
        binding.rvPokemon.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPokemon.adapter = adapter

        //El scroll irá deteniendo automáticamente las CardView en el centro de la pantalla
        var scrollStopper = LinearSnapHelper()
        scrollStopper.attachToRecyclerView(binding.rvPokemon)
    }

    private fun searchByName(query: String) {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            //primera petición
            val nameResponse: Response<PokemonListResponse> =
                retrofit.create(APIService::class.java).getAllPokemon()
            if (nameResponse.isSuccessful) {
                val response: PokemonListResponse? = nameResponse.body()
                if (response != null) {
                    val filteredPokemon = response.results.filter {
                        it.name.startsWith(query, ignoreCase = true)
                    }
                    //segunda petición
                    val detailedPokemonList = mutableListOf<PokemonInfoResponse>()
                    for (pkData in filteredPokemon.map { it.name }) {
                        val detailedPokemonResponse: Response<PokemonInfoResponse> =
                            retrofit.create(APIService::class.java).getPokemon(pkData)
                        if (detailedPokemonResponse.isSuccessful) {
                            val detailedPokemon: PokemonInfoResponse? = detailedPokemonResponse.body()
                            if (detailedPokemon != null) {
                                detailedPokemonList.add(detailedPokemon)
                            }
                        }
                    }
                    runOnUiThread {
                        adapter.updateList(filteredPokemon, detailedPokemonList)
                        binding.progressBar.isVisible = false
                    }
                }
            } else {
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun navigateToDetail(id: String) {
        /*
        val intent = Intent(this, PokemonDetailActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
        */
    }
}