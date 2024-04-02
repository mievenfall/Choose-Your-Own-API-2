package com.example.pokemonsgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    var pokemonName = ""
    var pokemonID = ""
    private lateinit var pokemonList: MutableList<String>
    private lateinit var rvPokemons: RecyclerView
    private lateinit var tvName: TextView
    private lateinit var tvID: TextView
    private lateinit var pokemonImageURLs: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvPokemons = findViewById(R.id.rvPokemons)
        pokemonList = mutableListOf()
        val button = findViewById<Button>(R.id.bRandom)
        tvName = findViewById(R.id.tvName)
        tvID = findViewById(R.id.tvID)

        button.setOnClickListener {
            getPokemonImageURL()
        }
    }

    private fun getPokemonImageURL() {
        val client = AsyncHttpClient()
        val rnds = (0..1025).random()
        val str = "https://pokeapi.co/api/v2/pokemon-form/$rnds"
        client[str, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val sprites = json.jsonObject.getJSONObject("sprites")
                val frontDefault = sprites.getString("front_default")
                val backDefault = sprites.optString("back_default", null)
                val frontShiny = sprites.optString("front_shiny", null)
                val backShiny = sprites.optString("back_shiny", null)
                pokemonImageURLs = listOf(frontDefault, backDefault, frontShiny, backShiny).filterNotNull()
                pokemonName = json.jsonObject.getString("name")
                pokemonID = json.jsonObject.getString("id")

                pokemonList.clear()
                pokemonList.addAll(pokemonImageURLs)

                val adapter = PokemonAdapter(pokemonList)
                rvPokemons.adapter = adapter
                rvPokemons.layoutManager = LinearLayoutManager(this@MainActivity)

                tvName.text = pokemonName
                tvID.text = pokemonID}

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog", errorResponse)
            }
        }]
    }
}