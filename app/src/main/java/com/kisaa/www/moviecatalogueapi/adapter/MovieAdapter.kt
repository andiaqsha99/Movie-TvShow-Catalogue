package com.kisaa.www.moviecatalogueapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kisaa.www.moviecatalogueapi.R
import com.kisaa.www.moviecatalogueapi.model.Movies
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class MovieAdapter(private val listener: (Movies) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val mMovies = ArrayList<Movies>()

    fun setData(data: ArrayList<Movies>) {
        mMovies.clear()
        mMovies.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list,
            parent, false
        )
        return MovieViewHolder(mView)
    }

    override fun getItemCount() = mMovies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindItem(mMovies[position], listener)
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(movies: Movies, listener: (Movies) -> Unit) {
            with(itemView) {
                txt_title.text = movies.title
                txt_description.text = if (movies.overview != "") {
                    movies.overview
                } else {
                    resources.getString(R.string.no_descrription)
                }
                txt_grade.text = movies.vote_average
                setOnClickListener {
                    listener(movies)
                }
            }
            Picasso.get().load("https://image.tmdb.org/t/p/w185${movies.poster_path}")
                .into(itemView.img_photo)
        }
    }
}