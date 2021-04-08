package com.example.retrofittest.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.retrofittest.R
import com.example.retrofittest.databinding.NewsDetailsFragmentBinding

class DetailsFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding=NewsDetailsFragmentBinding.inflate(inflater,container,false)
        binding.news=navArgs<DetailsFragmentArgs>().value.News
        binding.lifecycleOwner=this
        return binding.root
    }
}