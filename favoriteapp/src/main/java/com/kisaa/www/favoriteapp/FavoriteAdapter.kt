package com.kisaa.www.favoriteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class FavoriteAdapter(private val listener: (Favorite) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val favorites = ArrayList<Favorite>()

    fun setData(data: List<Favorite>) {
        favorites.clear()
        favorites.addAll(data)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(favorite: Favorite, listener: (Favorite) -> Unit) {
            with(itemView) {
                txt_grade.text = favorite.voteAverage
                txt_title.text = favorite.title
                txt_description.text =
                    if (favorite.overview != "") {
                        favorite.overview
                    } else {
                        resources.getString(R.string.no_description)
                    }
                setOnClickListener {
                    listener(favorite)
                }
            }

            Picasso.get().load("https://image.tmdb.org/t/p/w185${favorite.posterPath}")
                .into(itemView.img_photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val mView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return FavoriteViewHolder(mView)
    }

    override fun getItemCount(): Int = favorites.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites[position], listener)
    }
}