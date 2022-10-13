package com.mycompany.footballist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mycompany.footballist.R
import com.mycompany.footballist.utils.BaseFragment
import com.mycompany.footballist.utils.CURRENT_SEASON
import com.mycompany.footballist.utils.downloadFromUrl
import com.mycompany.footballist.utils.placeHolderProgressBar
import com.mycompany.footballist.viewmodel.PlayerStatsViewModel
import kotlinx.android.synthetic.main.fragment_player_stats.*


class PlayerStatsFragment : BaseFragment() {

    private lateinit var viewModel : PlayerStatsViewModel
    var playerId : Int = 0
    var playerPosition : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if(bundle != null){
            val args = PlayerStatsFragmentArgs.fromBundle(bundle)
            playerId = args.playerId
            playerPosition = args.playerPosition!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_stats, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlayerStatsViewModel::class.java)
        viewModel.refreshPlayerDetails(playerId, CURRENT_SEASON)
        observeLiveData()

    }

    private fun observeLiveData(){
        viewModel.playerDetails.observe(viewLifecycleOwner, Observer {playerDetails ->
            playerDetails?.let {
                val playerShotsString = "${playerDetails.shotsOnTarget}/${playerDetails.shotsTotal}"
                tv_player_shots.text = playerShotsString
                //val playerPassString = "${playerDetails.passesTotal}/(%${playerDetails.passesOnTarget})"
                val playerDuelsString = "${playerDetails.wonDuel}/${playerDetails.totalDuel}"
                tv_player_duels.text = playerDuelsString
                tv_player_passes.text = playerDetails.passesTotal.toString()
                tv_player_goals.text = playerDetails.goals.toString()
                tv_player_country.text = playerDetails.playerNationality
                tv_player_name.text = playerDetails.playerName
                tv_player_height.text = playerDetails.playerHeight
                tv_player_weight.text = playerDetails.playerWeight
                tv_player_age.text = playerDetails.playerAge.toString()
                tv_player_assists.text = playerDetails.assists.toString()
                iv_playerImage.downloadFromUrl(playerDetails.playerPhotoUrl, placeHolderProgressBar(requireContext()))
                if(playerPosition == "Goalkeeper"){
                    tv_goals_conceded.text = playerDetails.goalsConceded.toString()
                    tv_saved_shots.text = playerDetails.savedShots.toString()
                }
                else{
                    ll_conceded_goals.visibility = View.GONE
                    ll_saved_shots.visibility = View.GONE
                }
            }
        })

        viewModel.playerDetailsError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(it){
                    showErrorSnackBar(getString(R.string.network_error_occurred),true)
                }
                else{
                    println("error gone")
                }
            }
        })

        viewModel.playerDetailsLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    iv_playerImage.visibility = View.GONE
                    ll_player_personal_info.visibility = View.GONE
                    ll_match_stats.visibility = View.GONE
                    showProgressBar(getString(R.string.please_wait))
                }
                else{
                    iv_playerImage.visibility = View.VISIBLE
                    ll_player_personal_info.visibility = View.VISIBLE
                    ll_match_stats.visibility = View.VISIBLE
                    hideProgressBar()
                }
            }
        })
    }


}