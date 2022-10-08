package com.reyndev.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reyndev.newsapp.databinding.ItemNewsBinding
import com.reyndev.newsapp.model.Article
import com.squareup.picasso.Picasso

class NewsAdapter(
    private val listener: (Article) -> Unit
): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    inner class NewsViewHolder(val binding: ItemNewsBinding)
        : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var articles: List<Article>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        return NewsViewHolder(ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        val article = articles[position]

        holder.binding.apply {
            Picasso.get()
                .load(article.urlToImage)
                .resize(64, 64)
                .centerCrop()
                .into(ivImage)
            tvTitle.text = article.title
            tvSubtitle.text = article.description
        }

        holder.itemView.setOnClickListener { listener(article) }
    }

    override fun getItemCount(): Int = articles.size

}