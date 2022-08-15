package com.vunh.recycler_hilt_clean.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vunh.recycler_hilt_clean.model.Movie
import com.vunh.recycler_hilt_clean_clean.R

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var movieList =  listOf<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
        holder.delete.setOnClickListener {
            onDeleteItemListener?.let { it(movie.movieId) }
        }
        holder.update.setOnClickListener {
            onUpdateItemListener?.let { it(movie) }
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val movieThubmnail: ImageView = itemView.findViewById(R.id.movie_thumbnail)
        val movieId: TextView = itemView.findViewById(R.id.movie_id)
        val name: TextView = itemView.findViewById(R.id.name)
        val desc: TextView = itemView.findViewById(R.id.desc)
        val delete: ImageButton = itemView.findViewById(R.id.delete)
        val update: ImageButton = itemView.findViewById(R.id.update)

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.activity_recycler_item, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: Movie) {
            Glide.with(itemView.context).load(item.imageUrl).into(movieThubmnail)
            movieId.text = item.movieId
            name.text = item.name
            desc.text = item.desc
        }
    }

    private var onItemClickListener: ((Movie) -> Unit)? = null
    private var onDeleteItemListener: ((String) -> Unit)? = null
    private var onUpdateItemListener: ((Movie) -> Unit)? = null

    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }

    fun deleteItemListener(listener: (String) -> Unit) {
        onDeleteItemListener = listener
    }

    fun updateItemListener(listener: (Movie) -> Unit) {
        onUpdateItemListener = listener
    }
}