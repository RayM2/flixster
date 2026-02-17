package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieTitle = itemView.findViewById<TextView>(R.id.movie_title)
        private val movieDescription = itemView.findViewById<TextView>(R.id.movie_description)
        private val moviePoster = itemView.findViewById<ImageView>(R.id.movie_poster)

        fun bind(movie: Movie) {
            movieTitle.text = movie.title
            movieDescription.text = movie.overview
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
                .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                .error(R.drawable.ic_launcher_background) // Error image
                .into(moviePoster)
        }
    }
}