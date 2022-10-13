package com.mycompany.footballist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.mycompany.footballist.R
import com.mycompany.footballist.model.teams.Venue
import com.mycompany.footballist.utils.BaseFragment
import com.mycompany.footballist.utils.downloadFromUrl
import com.mycompany.footballist.utils.placeHolderProgressBar
import com.mycompany.footballist.viewmodel.TeamDetailsViewModel
import kotlinx.android.synthetic.main.fragment_team_details.*


class TeamDetailsFragment : BaseFragment() {

    private lateinit var viewModel : TeamDetailsViewModel
    private var teamId : Int = 0
    private var stadium : Venue = Venue()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments

        if(bundle != null){
            val args = TeamDetailsFragmentArgs.fromBundle(bundle)
            teamId = args.teamId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_details, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamDetailsViewModel::class.java)
        viewModel.refreshTeam(teamId)
        observeLiveData()

        tb_see_players_details.setOnClickListener {
            moveToPlayerDetails(teamId)
        }

        tb_see_stadium_details.setOnClickListener {
            moveToStadiumDetails()
        }
    }


    private fun observeLiveData(){
        viewModel.team.observe(viewLifecycleOwner, Observer {team ->
            team?.let {
                tv_teamName_details.text = team.name
                tv_name_country_team_details.text = team.country
                tv_found_date_team_details.text = team.founded.toString()
                tv_short_name_team_details.text = team.code
                imageView.downloadFromUrl(team.logo, placeHolderProgressBar(requireActivity()))

            }
        })

        viewModel.teamVenue.observe(viewLifecycleOwner, Observer { venue ->
            venue?.let {
                tv_stadium_team_details.text = venue.name
                tv_city_team_details.text = venue.city
                //venue capacity = venue.capacity
                stadium.name = venue.name
                stadium.address = venue.address
                stadium.capacity = venue.capacity
                stadium.id = venue.id
                stadium.image = venue.image
                stadium.surface = venue.surface
                stadium.city = venue.city
            }
        })

        viewModel.teamError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(it){
                    team_details_layout.visibility = View.GONE
                    showErrorSnackBar(getString(R.string.no_standing_data_available),true)
                }
                else{
                    team_details_layout.visibility = View.VISIBLE
                }
            }
        })

        viewModel.teamLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    team_details_layout.visibility = View.GONE
                    showProgressBar(getString(R.string.please_wait))
                }
                else{
                    team_details_layout.visibility = View.VISIBLE
                    hideProgressBar()
                }
            }
        })

    }

    private fun moveToPlayerDetails(teamID : Int){
        val action = TeamDetailsFragmentDirections.actionTeamDetailsFragmentToPlayersFragment(teamID)
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun moveToStadiumDetails(){
        val action = TeamDetailsFragmentDirections.actionTeamDetailsFragmentToStadiumDetailsFragment(stadium)
        Navigation.findNavController(requireView()).navigate(action)
    }


}