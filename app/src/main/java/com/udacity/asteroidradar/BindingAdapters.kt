package com.udacity.asteroidradar

import android.media.Image
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.udacity.asteroidradar.main.AsteroidListAdapter
import com.udacity.asteroidradar.main.PictureLoadingStatus
import timber.log.Timber

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
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



@BindingAdapter("recyclerViewData")
fun  RecyclerView.displayRecyclerViewData(data : List<Asteroid>?){

    //get adapter
    val adapter = this.adapter as AsteroidListAdapter

    //notifyDataSetChanged
    adapter.submitList(data)




}


@BindingAdapter("pictureOfTheDay")

fun ImageView.showPicture(pictureOfDay: PictureOfDay?){


    load(pictureOfDay?.url)

    Timber.i("$pictureOfDay")
}

@BindingAdapter("pictureLoadingStatus")
fun ImageView. pictureLoadingStatus(status:PictureLoadingStatus?){

    when(status){

        PictureLoadingStatus.DONE -> {

            visibility = GONE
        }


        PictureLoadingStatus.ERROR -> {

            visibility = VISIBLE
            setImageResource(R.drawable.ic_connection_error)
        }

        PictureLoadingStatus.LOADING -> {

            visibility = VISIBLE
            setImageResource(R.drawable.loading_animation)
        }

    }
}
