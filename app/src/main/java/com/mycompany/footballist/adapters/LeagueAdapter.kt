package com.mycompany.footballist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycompany.footballist.ui.LeagueFeedFragment
import com.mycompany.footballist.R
import com.mycompany.footballist.model.league.League
import com.mycompany.footballist.utils.downloadFromUrl
import com.mycompany.footballist.utils.placeHolderProgressBar
import kotlinx.android.synthetic.main.league_row.view.*


class LeagueAdapter(private var leagueList: ArrayList<League>,val fragment: LeagueFeedFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.league_row,parent,false)
        return LeagueViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = leagueList[position]
        holder.itemView.leagueName.text = model.name
        holder.itemView.iv_leagueImage.downloadFromUrl(model.logo, placeHolderProgressBar(holder.itemView.context))
        println("league id = "+model.id)
        holder.itemView.setOnClickListener {
            fragment.moveToStandings(model.id,2022)
        }
    }

    override fun getItemCount(): Int {
        return leagueList.size
    }

    fun updateLeagueList(newLeagueList : List<League>){
        leagueList.clear()
        leagueList.addAll(newLeagueList)
        notifyDataSetChanged()
    }


}