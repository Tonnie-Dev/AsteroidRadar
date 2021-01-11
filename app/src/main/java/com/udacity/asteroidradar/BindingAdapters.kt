package com.udacity.asteroidradar

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.udacity.asteroidradar.main.AsteroidListAdapter
import timber.log.Timber

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    }
    else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    }
    else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}


//Binding adapter for RecyclerView's Data
@BindingAdapter("recyclerViewData")
fun RecyclerView.displayRecyclerViewData(data: List<Asteroid>?) {

    //get adapter
    val adapter = this.adapter as AsteroidListAdapter

    //notifyDataSetChanged
    adapter.submitList(data)

}

//Binding Adapter for pictureOfTheDay using Kotlin's Coil  Library to load image
@BindingAdapter("pictureOfTheDay")

fun ImageView.showPicture(pictureOfDay: PictureOfDay?) {

    load(pictureOfDay?.url){

        placeholder(R.drawable.loading_animation)
        error(R.drawable.ic_broken_image)
    }

}

