package com.androidshowtime.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androidshowtime.asteroidradar.DateFilter
import com.androidshowtime.asteroidradar.DurationRange
import com.androidshowtime.asteroidradar.R
import com.androidshowtime.asteroidradar.databinding.FragmentMainBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    //late init for binding
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)

        //make binding observeLive Data
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        //set recyclerView's Adapter

        binding.asteroidRecycler.adapter = AsteroidListAdapter(OnClickListener {

            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        })


        // add YouTubePlayerView as a lifecycle observer of its parent Activity/Fragment
        lifecycle.addObserver(binding.youtubePlayerView)


        //observe picture of the day media type
        viewModel.pictureOfTheDay.observe(viewLifecycleOwner) { media ->
            // mediaType is a Video- show YouTubePlayer View
            if (media.mediaType == "video") {

                //show YouTubePlayer View
                binding.youtubePlayerView.visibility = View.VISIBLE

                //get video url
                val videoUrl = media.url


                binding.youtubePlayerView.addYouTubePlayerListener(object : YouTubePlayerListener {
                    override fun onApiChange(youTubePlayer: YouTubePlayer) {

                    }

                    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {

                    }

                    override fun onError(
                        youTubePlayer: YouTubePlayer,
                        error: PlayerConstants.PlayerError
                    ) {

                    }

                    override fun onPlaybackQualityChange(
                        youTubePlayer: YouTubePlayer,
                        playbackQuality: PlayerConstants.PlaybackQuality
                    ) {

                    }

                    override fun onPlaybackRateChange(
                        youTubePlayer: YouTubePlayer,
                        playbackRate: PlayerConstants.PlaybackRate
                    ) {

                    }


                    ////set url
                    override fun onReady(youTubePlayer: YouTubePlayer) {

                        val url = media.url
                        val id = extractVideoId(url)!!

                        youTubePlayer.loadVideo(id, 0F)
                    }

                    override fun onStateChange(
                        youTubePlayer: YouTubePlayer,
                        state: PlayerConstants.PlayerState
                    ) {

                    }

                    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {

                    }

                    override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {

                    }

                    override fun onVideoLoadedFraction(
                        youTubePlayer: YouTubePlayer,
                        loadedFraction: Float
                    ) {

                    }
                })


            } else {

                // if mediaType is a Picture - Hide YouTubePlayer View
                binding.youtubePlayerView.visibility = View.GONE
            }

        }
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.show_today_asteroid -> {

                viewModel.updateRange(DurationRange.RANGE_TODAY)
                binding.asteroidRecycler.scrollToPosition(0)
                return true
            }
            R.id.show_week_asteroids -> {

                viewModel.updateRange(DurationRange.RANGE_ONE_WEEK)
                binding.asteroidRecycler.scrollToPosition(0)
                return true
            }
            R.id.show_saved_asteroids -> {


                viewModel.updateRange(DurationRange.RANGE_ALL_TIME)
                binding.asteroidRecycler.scrollToPosition(0)
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun extractVideoId(url: String): String? {
        var videoId: String? = null
        val regex =
            "^((?:https?:)?//)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be|youtube-nocookie.com))(/(?:[\\w\\-]+\\?v=|feature=|watch\\?|e/|embed/|v/)?)([\\w\\-]+)(\\S+)?\$"
        val pattern = Pattern.compile(regex , Pattern.CASE_INSENSITIVE
        )
        val matcher= pattern.matcher(url)
        if (matcher.matches()) {
            videoId = matcher.group(5)
        }
        return videoId
    }
}
