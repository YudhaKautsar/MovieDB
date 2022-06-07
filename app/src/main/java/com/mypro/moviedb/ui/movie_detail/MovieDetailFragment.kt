package com.mypro.moviedb.ui.movie_detail

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
import com.mypro.moviedb.BuildConfig
import com.mypro.moviedb.R
import com.mypro.moviedb.databinding.FragmentMovieDetailBinding
import com.mypro.moviedb.util.SnackBarType
import com.mypro.moviedb.util.Utils.showSnackBar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()
    private val adapter by lazy { ReviewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeToLiveData()
    }

    private fun initView() {
        binding.apply {
            lifecycle.addObserver(ytPlayer)

            rvReview.layoutManager = LinearLayoutManager(requireContext())
            rvReview.adapter = adapter

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun subscribeToLiveData() {
        viewModel.apply {
            getMovie(args.id, BuildConfig.API_KEY)
            getMovieVideos(args.id, BuildConfig.API_KEY)
            getReviews(args.id)

            movieLiveData.observe(viewLifecycleOwner) {
                binding.movie = it
            }

            movieVideosLiveData.observe(viewLifecycleOwner) {
                val videoId = it.firstOrNull { it.type == "Trailer" }?.key

                binding.ytPlayer.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        videoId?.let { it1 -> youTubePlayer.loadVideo(it1, 0f) }
                    }
                })
            }

            viewLifecycleOwner.lifecycleScope.launch {
                getReviews(args.id).collectLatest { movies ->
                    adapter.submitData(movies)
                }
            }

            errorLiveData.observe(viewLifecycleOwner) {
                showSnackBar(binding.root, it, SnackBarType.ERROR)
            }
        }
    }
}
