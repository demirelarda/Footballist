package com.mycompany.footballist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.mycompany.footballist.ui.PlayersFragment
import com.mycompany.footballist.R
import com.mycompany.footballist.model.players.Player
import com.mycompany.footballist.utils.downloadFromUrl
import kotlinx.android.synthetic.main.player_row.view.*

class PlayerAdapter(private var playerList : ArrayList<Player>, val fragment: PlayersFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.player_row,parent,false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = playerList[position]
        holder.itemView.tv_player_name.text = model.name
        holder.itemView.tv_player_age.text = model.age.toString()
        holder.itemView.tv_player_position.text = model.position
        holder.itemView.iv_playerImage.downloadFromUrl(model.photo, CircularProgressDrawable(holder.itemView.context))
        holder.itemView.setOnClickListener {
            fragment.moveToPlayerStats(model.id,model.position)
        }
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    fun updatePlayerList(newPlayerList : List<Player>){
        playerList.clear()
        playerList.addAll(newPlayerList)
        notifyDataSetChanged()
    }


}