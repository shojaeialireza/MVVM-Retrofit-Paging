package com.example.retrofittest.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofittest.R
import com.example.retrofittest.databinding.NewsItemLayoutBinding
import com.example.retrofittest.model.remote.datamodel.News
import com.example.retrofittest.view.ListFragmentDirections

class NewsListAdapter():PagingDataAdapter<News, NewsListAdapter.VH>(DIFF_CALLBACKS){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding:NewsItemLayoutBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.news_item_layout,parent,false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position)!!)
    }

    companion object{
        private val DIFF_CALLBACKS=object :DiffUtil.ItemCallback<News>(){
            override fun areItemsTheSame(oldItem: News, newItem: News)=
                oldItem.published==newItem.published

            override fun areContentsTheSame(oldItem: News, newItem: News)=
                oldItem==newItem

        }
    }

    class VH(var binding:NewsItemLayoutBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(news: News){
            binding.news=news

            binding.root.setOnClickListener {
                it.findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailsFragment(news))
            }
        }

    }
}