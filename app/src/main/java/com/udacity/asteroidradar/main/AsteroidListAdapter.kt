package com.udacity.asteroidradar.main


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.AsteroidListItemBinding

class AsteroidListAdapter(private val clickListener: OnClickListener) : ListAdapter<Asteroid, AsteroidListAdapter.ViewHolder>(
        DiffClass()) {


    //inflate ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


       return ViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = getItem(position)

        //view.setOnClickListener
        holder.itemView.setOnClickListener {

            clickListener.onClick(asteroid)
        }
        holder.bind(asteroid)
    }

    class ViewHolder(private val binding: AsteroidListItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(asteroid: Asteroid?) {

            binding.asteroid = asteroid
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                val inflater = LayoutInflater.from(parent.context)

                val binding =
                        DataBindingUtil.inflate<AsteroidListItemBinding>(
                                inflater, R.layout.asteroid_list_item, parent, false)
                return ViewHolder(binding)

            }

        }


    }

}
//Diff Class
class DiffClass : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {

        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {

        return oldItem == newItem
    }

}
//onclickListener

class OnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}