package com.udacity.asteroidradar.main


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid

class AsteroidListAdapter(): ListAdapter<Asteroid, AsteroidListAdapter.ViewHolder>(DiffClass()) {







    class ViewHolder(private val binding:View) :RecyclerView.ViewHolder( binding){



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

class DiffClass : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {

        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {

        return oldItem ==newItem
    }

}
