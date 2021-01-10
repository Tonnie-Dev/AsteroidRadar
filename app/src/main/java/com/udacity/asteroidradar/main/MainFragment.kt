package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater)

        //make binding observeLive Data
        binding.lifecycleOwner = this

        binding.viewModel = viewModel




        //set recyclerView's Adapter

        binding.asteroidRecycler.adapter = AsteroidListAdapter(OnClickListener {


            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        })


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){

            R.id.show_today_asteroid -> {
                return true}
            R.id.show_week_asteroids -> {
                return true}
            R.id.show_saved_asteroids -> {
                return true}

            else ->  super.onOptionsItemSelected(item)
        }
    }
}
