package com.example.retrofittest.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofittest.databinding.LoadStateLayoutBinding

class NewsLoadStateAdapter(private var retry:()->Unit) :LoadStateAdapter<NewsLoadStateAdapter.StateVH>(){

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): StateVH {
        return StateVH(LoadStateLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: StateVH, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class StateVH(private val binding:LoadStateLayoutBinding):RecyclerView.ViewHolder(binding.root){

        init {
            binding.loadAgainBtn.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState){
            binding.apply {
                loadingProgressBar.isVisible=loadState is LoadState.Loading
                loadErrorTextView.isVisible=loadState !is LoadState.Loading
                loadAgainBtn.isVisible=loadState !is LoadState.Loading
            }
        }
    }
}