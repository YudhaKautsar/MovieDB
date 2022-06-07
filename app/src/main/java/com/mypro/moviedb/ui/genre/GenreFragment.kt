package com.mypro.moviedb.ui.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mypro.moviedb.BuildConfig
import com.mypro.moviedb.R
import com.mypro.moviedb.databinding.FragmentGenreBinding
import com.mypro.moviedb.util.GridSpacingItemDecoration
import com.mypro.moviedb.util.SnackBarType
import com.mypro.moviedb.util.Utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreFragment : Fragment() {
    private lateinit var binding: FragmentGenreBinding
    private val viewModel: GenreViewModel by viewModels()
    private val adapter by lazy { GenreAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_genre, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeToLiveData()
    }

    private fun initView() {
        binding.apply {
            title = getString(R.string.genres)

            rvGenre.layoutManager = GridLayoutManager(requireContext(), 2)
            rvGenre.adapter = adapter
            rvGenre.addItemDecoration(GridSpacingItemDecoration(2, 24, true))
            adapter.onClick = {
                findNavController().navigate(
                    GenreFragmentDirections.actionGenreFragmentToMovieFragment(
                        it
                    )
                )
            }
        }
    }

    private fun subscribeToLiveData() {
        viewModel.apply {
            getGenres(BuildConfig.API_KEY)

            genresLiveData.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            errorLiveData.observe(viewLifecycleOwner) {
                showSnackBar(binding.root, it, SnackBarType.ERROR)
            }
        }
    }

}