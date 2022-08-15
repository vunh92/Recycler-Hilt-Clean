package com.vunh.recycler_hilt_clean.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.vunh.recycler_hilt_clean.BaseApp
//import com.vunh.recycler_hilt_clean.BaseApp
import com.vunh.recycler_hilt_clean.model.Movie
import com.vunh.recycler_hilt_clean.viewModel.detail_movie.DetailMovieViewModel
import com.vunh.recycler_hilt_clean_clean.databinding.ActivityDetailMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private val viewModel: DetailMovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeView()
        initializeViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeView(){
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.title = "Detail"

        val bundle: Bundle? = intent.getBundleExtra("bundleDetailMovie")
        bundle?.let {
            bundle.apply {
                val movie = getSerializable("movie") as Movie?
                if(movie != null) {
                    viewModel.getMovie(movie.movieId)
                }
            }
        }
    }

    private fun initializeViewModel(){
        viewModel.showResult.observe(this, Observer {
            if(it != null) {
                Snackbar.make(binding.root, "Success", Snackbar.LENGTH_LONG).show()
                Glide.with(this).load(it.imageUrl).into(binding.detailMovieThumbnail);
                binding.detailCategoryTv.setText(it.category)
                binding.detailNameTv.setText(it.name)
                binding.detailDescTv.setText(it.desc)
            }
        })
        viewModel.showLoading.observe(this, Observer {
            if (it)
                binding.detailLoadingSpinner.visibility = View.VISIBLE
            else
                binding.detailLoadingSpinner.visibility = View.GONE
        })
        viewModel.showError.observe(this, Observer {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        })
    }
}