package com.udacity.asteroidradar.main


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidListItemBinding

class AsteroidListAdapter() : ListAdapter<Asteroid, AsteroidListAdapter.ViewHolder>(DiffClass()) {


    //inflate ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        return ViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid)
    }

    class ViewHolder(private val binding: AsteroidListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(asteroid: Asteroid?) {

            binding.asteroid = asteroid
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                val inflater = LayoutInflater.from(parent.context)

                return ViewHolder(AsteroidListItemBinding.inflate(inflater))

            }

        }


    }

}

class DiffClass : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {

        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {

        return oldItem == newItem
    }

}
