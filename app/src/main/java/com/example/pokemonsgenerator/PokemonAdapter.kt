package com.example.pokemonsgenerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokemonAdapter(private val pokemonList: List<String>) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonImage: ImageView = view.findViewById(R.id.pokemon_image)
        val tvLabel: TextView = view.findViewById(R.id.tv_label)
        val tvImageLink: TextView = view.findViewById(R.id.tv_image_link)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = pokemonList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = pokemonList[position]
        Glide.with(holder.itemView)
            .load(imageUrl)
            .centerCrop()
            .into(holder.pokemonImage)

        val label = when (position) {
            0 -> "Front Default"
            1 -> "Back Default"
            2 -> "Front Shiny"
            3 -> "Back Shiny"
            else -> ""
        }
        holder.tvLabel.text = label
        holder.tvLabel.text = label
        holder.tvImageLink.text = imageUrl
    }

}