package com.udacity.asteroidradar.screen.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapter.AsteroidListAdapter
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.network.NetworkApiProvider
import com.udacity.asteroidradar.repository.AsteroidRepository

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val database = AsteroidDatabase.getInstance(requireContext())
        val repository = AsteroidRepository(
            database,
            NetworkApiProvider.asteroidApi,
            NetworkApiProvider.pictureOfDayApi
        )
        val viewModelFactory = MainViewModelFactory(repository, requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.viewModel = viewModel

        setupUI(binding)

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupUI(binding: FragmentMainBinding) {
        val adapter = AsteroidListAdapter(AsteroidListAdapter.ClickListener {
            viewModel.onClickAsteroidItem(it)
        })
        binding.asteroidRecycler.adapter = adapter

        viewModel.asteroids.observe(viewLifecycleOwner, Observer { asteroids ->
            adapter.submitList(asteroids)
        })

        viewModel.showLoading.observe(viewLifecycleOwner, Observer { showLoading ->
            if (showLoading) {
                binding.statusLoadingWheel.visibility = View.VISIBLE
            } else {
                binding.statusLoadingWheel.visibility = View.GONE
            }
        })

        viewModel.navigateToDetailScreen.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.onNavigateToDetailScreenCompleted()
            }
        })

        viewModel.showErrorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                viewModel.onShowErrorMessageCompleted()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_today_menu -> viewModel.updateFilter(AsteroidApiFilter.SHOW_TODAY_ASTEROIDS)
            R.id.show_week_menu -> viewModel.updateFilter(AsteroidApiFilter.SHOW_WEEK_ASTEROIDS)
            R.id.show_saved_menu -> viewModel.updateFilter(AsteroidApiFilter.SHOW_SAVED_ASTEROIDS)
        }
        return true
    }
}
