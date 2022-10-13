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
import com.mycompany.footballist.adapters.LeagueAdapter
import com.mycompany.footballist.utils.BaseFragment
import com.mycompany.footballist.viewmodel.LeagueFeedViewModel
import kotlinx.android.synthetic.main.fragment_league_feed.*


class LeagueFeedFragment : BaseFragment() {

    private lateinit var viewModel : LeagueFeedViewModel
    private val leagueAdapter = LeagueAdapter(arrayListOf(),this)
    private var countryCode : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments

        if(bundle != null){
            val args = LeagueFeedFragmentArgs.fromBundle(bundle)
            if(args.countryCode != null){
                countryCode = args.countryCode!!
            }
            else{
                //empty
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LeagueFeedViewModel::class.java)
        viewModel.refreshLeagueData(countryCode)
        rv_leagueList.layoutManager = LinearLayoutManager(context)
        rv_leagueList.adapter = leagueAdapter

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.leagues.observe(viewLifecycleOwner, Observer {countries ->
            countries?.let {
                rv_leagueList.visibility = View.VISIBLE
                leagueAdapter.updateLeagueList(countries)
            }
        })

        viewModel.leagueError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(it){
                    rv_leagueList.visibility = View.GONE
                    tv_league_error.visibility = View.VISIBLE
                }
                else{
                    tv_league_error.visibility = View.GONE
                }
            }
        })

        viewModel.leagueLoading.observe(viewLifecycleOwner, Observer {
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

    fun moveToStandings(leagueId: Int, seasonNumber: Int){
        val action = LeagueFeedFragmentDirections.actionLeagueFeedFragmentToLeagueStandingsFragment(leagueId,seasonNumber)
        Navigation.findNavController(requireView()).navigate(action)
    }




}