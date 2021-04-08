package com.example.retrofittest.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.retrofittest.R
import com.squareup.picasso.Picasso

@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String?){
    Picasso.get().load(Uri.parse(url?:""))
            .placeholder(CircularProgressDrawable(this.context))
            .error(R.drawable.news)
            .into(this)
}
