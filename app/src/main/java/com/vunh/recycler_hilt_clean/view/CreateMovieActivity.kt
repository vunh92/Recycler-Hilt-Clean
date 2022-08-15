package com.vunh.recycler_hilt_clean.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.vunh.recycler_hilt_clean.model.Movie
import com.vunh.recycler_hilt_clean_clean.databinding.ActivityCreateMovieBinding
import com.vunh.recycler_hilt_clean_clean.viewModel.create_movie.CreateMovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateMovieBinding
    private val viewModel: CreateMovieViewModel by viewModels()
    var isUpdate = false
    var baseMovie = Movie(
        "",
        "High Rated",
        "https://howtodoandroid.com/images/wonder.jpg",
        "Wonder",
        "Wonder is a 2017 American drama film directed by Stephen Chbosky and written by Jack Thorne , Steve Conrad and Stephen Chbosky based on the 2012 novel of the same name by R.J. Palacio"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMovieBinding.inflate(layoutInflater)
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

        val bundle: Bundle? = intent.getBundleExtra("bundleCreateMovie")
        bundle?.let {
            bundle.apply {
                actionBar?.title = getString("title")
                val movie = getSerializable("movie") as Movie?
                if(movie != null) {
                    baseMovie = movie
                    isUpdate = true
                }
                binding.createCategoryEdt.setText(baseMovie.category)
                binding.createImageUrlEdt.setText(baseMovie.imageUrl)
                binding.createNameEdt.setText(baseMovie.name)
                binding.createDescEdt.setText(baseMovie.desc)
            }
        }
        binding.createAcceptBtn.setOnClickListener {
            if(viewModel.showLoading.value == true) return@setOnClickListener
            baseMovie.category = binding.createCategoryEdt.text.toString().trim()
            baseMovie.imageUrl = binding.createImageUrlEdt.text.toString().trim()
            baseMovie.name = binding.createNameEdt.text.toString().trim()
            baseMovie.desc = binding.createDescEdt.text.toString().trim()
            if (isUpdate) {
                viewModel.updateMovie(baseMovie)
            }else {
                viewModel.insertMovie(baseMovie)
            }
        }
    }

    private fun initializeViewModel(){
        viewModel.showResult.observe(this, Observer {
            if(it) Snackbar.make(binding.root, "Success", Snackbar.LENGTH_LONG).show()
            viewModel.finishActivity(this)
        })
        viewModel.showLoading.observe(this, Observer {
            if (it)
                binding.createLoadingSpinner.visibility = View.VISIBLE
            else
                binding.createLoadingSpinner.visibility = View.GONE
        })
        viewModel.showError.observe(this, Observer {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        })
    }
}