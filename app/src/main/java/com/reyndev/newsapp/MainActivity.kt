package com.reyndev.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.reyndev.newsapp.adapter.NewsAdapter
import com.reyndev.newsapp.api.RetrofitInstance
import com.reyndev.newsapp.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true

            val response = try {
                RetrofitInstance.api.getNews("Gaming", "id")
            } catch (e: IOException) {
                Log.e("MainActivity", "IOException error: ${e.toString()}")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e("MainActivity", "HttpException error: ${e.toString()}")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                newsAdapter.articles = response.body()!!.articles
            } else {
                Log.e("MainActivity", "Response failed")
            }

            binding.progressBar.isVisible = false
        }
    }

    fun setupRecyclerView() = binding.rvLayout.apply {
        newsAdapter = NewsAdapter() {
            article -> goToArticle(article.url)
        }
        adapter = newsAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun goToArticle(url: String) {
        Intent(this, ArticleActivity::class.java).also {
            val parsedUrl = url.replace("http:", "https:")
            it.putExtra("EXTRA_URL", url)
            startActivity(it)
        }
    }
}