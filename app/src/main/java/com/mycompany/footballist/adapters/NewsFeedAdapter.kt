package com.mycompany.footballist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycompany.footballist.R
import com.mycompany.footballist.model.news.Article
import com.mycompany.footballist.ui.FootballNewsFragment
import com.mycompany.footballist.utils.downloadFromUrl
import com.mycompany.footballist.utils.placeHolderProgressBar
import kotlinx.android.synthetic.main.news_row.view.*

class NewsFeedAdapter(private val fragment: FootballNewsFragment, private var newsTitleList : ArrayList<Article>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_row,parent,false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = newsTitleList[position]
        holder.itemView.tv_article_title.text = model.title
        holder.itemView.iv_news_image.downloadFromUrl(model.urlToImage, placeHolderProgressBar(holder.itemView.context))

        holder.itemView.setOnClickListener {
            fragment.moveToNewsDetails(model)
        }

    }

    override fun getItemCount(): Int {
        return newsTitleList.size
    }

    fun updateNewsList(newNewsList : List<Article>){
        newsTitleList.clear()
        newsTitleList.addAll(newNewsList)
        notifyDataSetChanged()
    }


}