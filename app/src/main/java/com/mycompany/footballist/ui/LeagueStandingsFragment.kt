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
import com.mycompany.footballist.adapters.LeagueStandingsAdapter
import com.mycompany.footballist.utils.BaseFragment
import com.mycompany.footballist.viewmodel.LeagueStandingsViewModel
import kotlinx.android.synthetic.main.fragment_league_standings.*


class LeagueStandingsFragment : BaseFragment() {

    private lateinit var viewModel : LeagueStandingsViewModel
    private val standingsAdapter = LeagueStandingsAdapter(arrayListOf(),this)
    private var leagueId : Int = 0
    private var season : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments

        if(bundle != null){
            val args = LeagueStandingsFragmentArgs.fromBundle(bundle)
            leagueId = args.leagueId
            season = args.seasonNumber
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league_standings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LeagueStandingsViewModel::class.java)
        viewModel.refreshLeagueStandings(leagueId,season)
        rv_standings_list.layoutManager = LinearLayoutManager(context)
        rv_standings_list.adapter = standingsAdapter

        observeLiveData()

    }

    private fun observeLiveData(){
        viewModel.standings.observe(viewLifecycleOwner, Observer {standings ->
            standings?.let {
                rv_standings_list.visibility = View.VISIBLE
                println(standings)
                standingsAdapter.updateStandings(standings)
            }
        })

        viewModel.standingsError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(it){
                    rv_standings_list.visibility = View.GONE
                    showErrorSnackBar(getString(R.string.no_standing_data_available),true)
                }
                else{
                    rv_standings_list.visibility = View.VISIBLE
                }
            }
        })

        viewModel.standingsLoading.observe(viewLifecycleOwner, Observer {
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

    fun moveToTeamDetails(id: Int){
        val action = LeagueStandingsFragmentDirections.actionLeagueStandingsFragmentToTeamDetailsFragment(id)
        Navigation.findNavController(requireView()).navigate(action)
    }



}