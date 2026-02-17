package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private val movies = mutableListOf<Movie>()
    private lateinit var moviesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesRecyclerView = findViewById(R.id.movie_list)
        val movieAdapter = MovieAdapter(this, movies)

        moviesRecyclerView.adapter = movieAdapter
        moviesRecyclerView.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()
        client.get("https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                try {
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    for (i in 0 until movieJsonArray.length()) {
                        val movieJsonObject = movieJsonArray.getJSONObject(i)
                        movies.add(
                            Movie(
                                movieJsonObject.getString("title"),
                                movieJsonObject.getString("overview"),
                                movieJsonObject.getString("poster_path")
                            )
                        )
                    }
                    movieAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.e("MainActivity", "Encountered exception: $e")
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String?, throwable: Throwable?) {
                Log.d("MainActivity", "onFailure")
            }
        })
    }
}