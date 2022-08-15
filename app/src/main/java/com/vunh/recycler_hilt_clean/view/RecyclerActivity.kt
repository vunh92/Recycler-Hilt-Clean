package com.vunh.recycler_hilt_clean.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vunh.recycler_hilt_clean.adapter.RecyclerAdapter
import com.vunh.recycler_hilt_clean.model.Movie
import com.vunh.recycler_hilt_clean.viewModel.recycler_view.RecyclerViewModel
import com.vunh.recycler_hilt_clean_clean.databinding.ActivityRecyclerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecyclerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerBinding
    private val viewModel: RecyclerViewModel by viewModels()
    lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
//        (application as BaseApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        viewModelFactory = RecyclerViewModelFactory(baseApp!!.recyclerRepositoryImpl)
//        viewModel = ViewModelProvider(this, viewModelFactory).get(RecyclerViewModel::class.java)

        setupRecyclerView()
        initializeView()
        initializeViewModel()
    }

    private fun initializeView(){
        binding.recyclerRefreshBtn.setOnClickListener(View.OnClickListener {
            if (viewModel.showLoading.value == false) {
                viewModel.clearMovie()
                viewModel.getMovieList()
            }
        })
        binding.recyclerClearBtn.setOnClickListener {
            viewModel.clearMovie()
        }
        recyclerAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("movie", it)
            }
            val intent = intentDetailMovieActivity(this)
            intent.putExtra("bundleDetailMovie", bundle)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
        recyclerAdapter.deleteItemListener {
            viewModel.deleteMovie(it)
        }
        recyclerAdapter.updateItemListener {
            val bundle = Bundle().apply {
                putSerializable("movie", it)
                putString("title", "Update")
            }
            val intent = intentCreateMovieActivity(this)
            intent.putExtra("bundleCreateMovie", bundle)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
        binding.recyclerInsertBtn.setOnClickListener {
            val bundle = Bundle().apply {
                putString("title", "Insert")
            }
            val intent = intentCreateMovieActivity(this)
            intent.putExtra("bundleCreateMovie", bundle)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }

    private fun initializeViewModel(){
        viewModel.showResult.observe(this, Observer {
            if(it) Snackbar.make(binding.root, "Success", Snackbar.LENGTH_LONG).show()
        })
        viewModel.showLoading.observe(this, Observer {
            if (it)
                binding.loadingSpinner.visibility = View.VISIBLE
            else
                binding.loadingSpinner.visibility = View.GONE
        })
        viewModel.showError.observe(this, Observer {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        })
        viewModel.movieList.observe(this, Observer<List<Movie>> {
            it?.apply {
                recyclerAdapter.movieList = it
            }
        })
    }

    private fun setupRecyclerView() {
        recyclerAdapter = RecyclerAdapter()
        binding.recyclerRv.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(this@RecyclerActivity)
        }
    }

    companion object {
        fun intentCreateMovieActivity(context: Context): Intent {
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return Intent(context, CreateMovieActivity::class.java)
        }
        fun intentDetailMovieActivity(context: Context): Intent {
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return Intent(context, DetailMovieActivity::class.java)
        }
    }
}