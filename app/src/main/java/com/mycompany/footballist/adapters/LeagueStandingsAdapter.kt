package com.mycompany.footballist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycompany.footballist.ui.LeagueStandingsFragment
import com.mycompany.footballist.R
import com.mycompany.footballist.model.standings.Standing
import com.mycompany.footballist.utils.downloadFromUrl
import com.mycompany.footballist.utils.placeHolderProgressBar
import kotlinx.android.synthetic.main.league_standings_row.view.*

class LeagueStandingsAdapter(private var standingsList : ArrayList<Standing>, private val fragment: LeagueStandingsFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class StandingsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.league_standings_row,parent,false)
        return StandingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = standingsList[position]
        holder.itemView.tv_team_standing.text = model.rank.toString()
        holder.itemView.tv_team_name_standing.text = model.team.name
        holder.itemView.tv_games_played_standing.text = model.all.played.toString()
        holder.itemView.tv_games_won_standings.text = model.all.win.toString()
        holder.itemView.tv_games_draw_standings.text = model.all.draw.toString()
        holder.itemView.tv_games_lost_standings.text = model.all.lose.toString()
        holder.itemView.tv_points_standings.text = model.points.toString()
        holder.itemView.tv_winLoseForm_standings.text = model.form
        holder.itemView.iv_standings_teamLogo.downloadFromUrl(model.team.logo,
            placeHolderProgressBar(holder.itemView.context))

        holder.itemView.setOnClickListener {
            fragment.moveToTeamDetails(model.team.id)
        }

    }

    override fun getItemCount(): Int {
        return standingsList.size
    }

    fun updateStandings(newStandings : List<Standing>){
        standingsList.clear()
        standingsList.addAll(newStandings)
        notifyDataSetChanged()
    }
}