package com.androidshowtime.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.androidshowtime.asteroidradar.main.AsteroidListAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
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


//Binding adapter for RecyclerView's Data
@BindingAdapter("recyclerViewData")
fun RecyclerView.displayRecyclerViewData(data: List<Asteroid>?) {


    //get adapter
    val adapter = this.adapter as AsteroidListAdapter

    //notifyDataSetChanged/ call diff
    adapter.submitList(data)

}

//Image ContentDesc Binding Adapter for hazard potential
@BindingAdapter("imageHazardContentDesc")
fun ImageView.imageHazardInfo(isHazardous: Boolean) {

    when (isHazardous) {

        true -> {
            this.contentDescription =
                resources.getString(R.string.potentially_hazardous_asteroid_image)
        }

        false -> {

            this.contentDescription = resources.getString(R.string.not_hazardous_asteroid_image)
        }
    }


}

//Image ContentDesc Binding Adapter for picture of the day
@BindingAdapter("imageOfTheDayContentDesc")
fun ImageView.imageOfTheDayContentDescription(pictureOfDay: PictureOfDay?) {

    val isImage = (pictureOfDay?.mediaType.equals("image"))

    when (isImage) {

        true -> {
            this.contentDescription = resources.getString(
                R.string
                    .nasa_picture_of_day_content_description_format, pictureOfDay?.title
            )
            visibility = View.VISIBLE
            Timber.i("The Picture of the Day is an Image")
        }

        false -> {
            Timber.i("The Picture of the Day is a Video")
            this.contentDescription =
                resources.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
            //hide imageView
            visibility = View.GONE
        }
    }


}

//Binding Adapter for pictureOfTheDay using Kotlin's Coil  Library to load image
@BindingAdapter("pictureOfTheDay")
fun ImageView.showPicture(pictureOfDay: PictureOfDay?) {
    load(pictureOfDay?.url) {
        placeholder(R.drawable.loading_animation)
        error(R.drawable.ic_broken_image)
    }

}
