package com.mypro.moviedb.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mypro.moviedb.R
import com.mypro.moviedb.databinding.FragmentMovieBinding
import com.mypro.moviedb.util.Utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private lateinit var binding: FragmentMovieBinding
    private val args: MovieFragmentArgs by navArgs()
    private val viewModel: MovieViewModel by viewModels()
    private val adapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscribeToLiveData()
    }

    private fun initView() {
        binding.apply {
            title = args.genre.name

            vToolbar.apply {
                btnBack.visible()
                btnBack.setOnClickListener {
                    findNavController().popBackStack()
                }
            }

            rvMovie.layoutManager = LinearLayoutManager(requireContext())
            rvMovie.adapter = adapter
            adapter.onClick = {
                findNavController().navigate(
                    MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
                        it
                    )
                )
            }
        }
    }

    private fun subscribeToLiveData() {
        viewModel.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                getMovies(args.genre.id).collectLatest { movies ->
                    adapter.submitData(movies)
                }
            }
        }
    }
}