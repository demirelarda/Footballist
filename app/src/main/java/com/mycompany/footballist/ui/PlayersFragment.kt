package com.mycompany.footballist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mycompany.footballist.R
import com.mycompany.footballist.adapters.PlayerAdapter
import com.mycompany.footballist.utils.BaseFragment
import com.mycompany.footballist.viewmodel.PlayersViewModel
import kotlinx.android.synthetic.main.fragment_players.*


class PlayersFragment : BaseFragment() {

    private lateinit var viewModel : PlayersViewModel
    private val playerAdapter = PlayerAdapter(arrayListOf(),this)
    var teamId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments

        if(bundle != null){
            val args = PlayersFragmentArgs.fromBundle(bundle)
            teamId = args.teamId
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_players, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlayersViewModel::class.java)
        viewModel.refreshTeamPlayers(teamId)
        rv_playerList.layoutManager = LinearLayoutManager(context)
        rv_playerList.adapter = playerAdapter

        observeLiveData()
    }



    private fun observeLiveData(){
        viewModel.players.observe(viewLifecycleOwner, Observer {players ->
            players?.let {
                rv_playerList.visibility = View.VISIBLE
                playerAdapter.updatePlayerList(players)
            }
        })

        viewModel.playersError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(it){
                    rv_playerList.visibility = View.GONE
                    showErrorSnackBar(getString(R.string.network_error_occurred),true)
                }
                else{
                    rv_playerList.visibility = View.VISIBLE
                }
            }
        })

        viewModel.playersLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    showProgressBar(getString(R.string.please_wait))
                }
                else{
                    hideProgressBar()
                }
            }
        })


    }

    fun moveToPlayerStats(playerId: Int,playerPosition: String){
        val action = PlayersFragmentDirections.actionPlayersFragmentToPlayerStatsFragment(playerId,playerPosition)
        Navigation.findNavController(requireView()).navigate(action)
    }


}