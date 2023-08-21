package com.kotlin.learn.feature.movie.presentation.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kotlin.learn.core.common.base.BaseFragment
import com.kotlin.learn.core.common.util.network.Result
import com.kotlin.learn.core.model.Genres
import com.kotlin.learn.core.model.MovieDetailModel
import com.kotlin.learn.core.nav.navigator.MovieNavigator
import com.kotlin.learn.core.utilities.Constant
import com.kotlin.learn.core.utilities.extension.launch
import com.kotlin.learn.feature.movie.adapter.DetailCreditsAdapter
import com.kotlin.learn.feature.movie.databinding.FragmentDetailBinding
import com.kotlin.learn.feature.movie.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var movieModel: MovieDetailModel

    private var movieKey = Constant.EMPTY_STRING

    private var movieId = Constant.EMPTY_STRING

    @Inject
    lateinit var movieNavigator: MovieNavigator

    override fun setupView() {
        subscribeDetail()
        loadArguments()
        setupVpCredits()
        setupListener()
    }

    private fun setupListener() = with(binding) {
        ivDetailHeaderDownloadIcon.setOnClickListener {
            Toast.makeText(requireContext(), "This feature under development", Toast.LENGTH_SHORT).show()
        }

        btnDetailPlayNow.setOnClickListener {
            if (movieKey != Constant.EMPTY_STRING)
                movieNavigator.fromDetailToVideos(this@DetailFragment, movieKey)
            else
                Toast.makeText(requireContext(), "Error get movie key", Toast.LENGTH_SHORT).show()
        }

        tvDetailCastSeeAll.setOnClickListener {
            movieNavigator.fromDetailToSeeAllCredits(this@DetailFragment, movieId)
        }
    }

    private fun subscribeDetail() = with(binding) {
        viewModel.detailMovies.launch(this@DetailFragment) {
            when (it) {
                is Result.Waiting -> {}

                is Result.Loading -> {
                    viewAnimator.displayedChild = Constant.ZERO
                }

                is Result.Success -> {
                    viewAnimator.displayedChild = Constant.ONE
                    loadContent(it.data)
                }

                is Result.Error -> {
                    viewAnimator.displayedChild = Constant.TWO
                }
            }
        }
        viewModel.detailVideos.launch(this@DetailFragment) {
            when (it) {
                is Result.Waiting -> {}

                is Result.Loading -> {}

                is Result.Success -> {
                    val model = it.data
                    for (i in 0..model.results.size) {
                        model.results[i].key?.let { key ->
                            movieKey = key
                        }
                        if (model.results[i].name == "Official Trailer") {
                            model.results[i].key?.let { key ->
                                movieKey = key
                            }
                            break
                        }
                    }
                }

                is Result.Error -> {}
            }
        }
    }

    private fun loadContent(movieDetailModel: MovieDetailModel) = with(binding) {
        movieModel = movieDetailModel
        movieModel.let {
            tvDetailHeaderTitle.text = it.title
            tvDetailHeaderRuleGenres.text = convertGenres(it.genres)
            tvDetailHeaderRuleTime.text = calculateRuntime(it.runtime)
            tvDetailHeaderRuleYear.text = convertDatetimeToDate(it.releaseDate)
            tvDetailHeaderDesc.text = it.overview
            setupThumbnail(it)
        }
    }

    private fun setupVpCredits() = with(binding) {
        val creditsAdapter = DetailCreditsAdapter(activity)
        creditsAdapter.addFragment(
            fragment = CreditsFragment(
                creditsCategory = CreditsCategory.CAST,
                movieId = movieId
            ),
            title = "Cast"
        )
        creditsAdapter.addFragment(
            fragment = CreditsFragment(
                creditsCategory = CreditsCategory.CREW,
                movieId = movieId
            ),
            title = "Director & Crew"
        )

        tlDetailCredits.apply {
            tabGravity = TabLayout.GRAVITY_FILL
        }
        vpDetailCredits.apply {
            adapter = creditsAdapter
            currentItem = Constant.ZERO
            isUserInputEnabled = Constant.FALSE
        }
        TabLayoutMediator(tlDetailCredits, vpDetailCredits) { tab, position ->
            tab.text = creditsAdapter.getTabTitle(position)
        }.attach()
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertDatetimeToDate(releaseDate: String?): String {
        releaseDate?.let {
            val currFormatter = SimpleDateFormat("yyyy")
            return currFormatter.format(currFormatter.parse(releaseDate)!!)
        }
        return Constant.EMPTY_STRING
    }

    private fun loadArguments() {
        movieId = args.movieId
        setupRemoteData(movieId)
    }

    private fun setupRemoteData(movieId: String) {
        viewModel.getDetailMovies(movieId = movieId)
        viewModel.getDetailVideos(movieId = movieId)
    }

    private fun calculateRuntime(timeRuntime: Int?): String {
        timeRuntime?.let {
            var startTime = timeRuntime
            var startHour = Constant.ZERO
            for (i in 0..startTime) {
                if (startTime < 60) {
                    break
                } else {
                    startTime -= 60
                    startHour++
                    continue
                }
            }
            return "${startHour}h ${timeRuntime % 60}m"
        }
        return Constant.EMPTY_STRING
    }

    private fun convertGenres(genres: ArrayList<Genres>): String {
        return genres[0].name.toString()
    }

    private fun setupThumbnail(movie: MovieDetailModel) = with(binding) {
        movie.backdropPath?.let {
            Glide.with(this@DetailFragment)
                .load("${Constant.BASE_URL_IMAGE_500}${it}")
                .into(ivDetailPlay)
        }
    }

}