package com.kisaa.www.moviecatalogueapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kisaa.www.moviecatalogueapi.R
import com.kisaa.www.moviecatalogueapi.model.TvShows
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class TvShowAdapter(private var listener: (TvShows) -> Unit) :
    RecyclerView.Adapter<TvShowAdapter.TvViewHolder>() {

    private var tvShows = ArrayList<TvShows>()

    fun setData(data: ArrayList<TvShows>) {
        tvShows.clear()
        tvShows.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val mView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return TvViewHolder(mView)
    }

    override fun getItemCount() = tvShows.size

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        holder.bindItem(tvShows[position], listener)
    }

    inner class TvViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(mTvShow: TvShows, listener: (TvShows) -> Unit) {
            with(itemView) {
                txt_title.text = mTvShow.name
                txt_description.text = if (mTvShow.overview != "") {
                    mTvShow.overview
                } else {
                    resources.getString(R.string.no_descrription)
                }
                txt_grade.text = mTvShow.vote_average
                setOnClickListener {
                    listener(mTvShow)
                }
            }
            Picasso.get().load("https://image.tmdb.org/t/p/w185${mTvShow.poster_path}")
                .into(itemView.img_photo)
        }
    }
}