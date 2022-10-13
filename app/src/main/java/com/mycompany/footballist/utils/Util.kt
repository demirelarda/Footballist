package com.mycompany.footballist.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mycompany.footballist.R
import com.squareup.picasso.Picasso

fun ImageView.downloadFromUrl(url : String?,progressDrawable: CircularProgressDrawable){

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_baseline_image_24)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)


}


fun placeHolderProgressBar(context: Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}


fun loadWithCoil(context: Context) : ImageLoader{
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()
    return imageLoader
}



















