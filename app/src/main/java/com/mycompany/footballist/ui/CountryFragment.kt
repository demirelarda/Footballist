package com.mycompany.footballist.ui

import android.os.Bundle
import android.view.*

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mycompany.footballist.R
import com.mycompany.footballist.adapters.CountryAdapter
import com.mycompany.footballist.utils.BaseFragment
import com.mycompany.footballist.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*


class CountryFragment : BaseFragment() {

    private lateinit var viewModel : CountryViewModel
    private val countryAdapter = CountryAdapter(arrayListOf(),this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)

    }






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.refreshData()


        rv_countryList.layoutManager = LinearLayoutManager(context)
        rv_countryList.adapter = countryAdapter

        swipeRefreshLayoutCountry.setOnRefreshListener {
            rv_countryList.visibility = View.GONE
            tv_country_error.visibility = View.GONE
            swipeRefreshLayoutCountry.isRefreshing = false
            viewModel.refreshFromAPI()

        }

        observeLiveData()

    }

    fun moveToLeagues(countryCode: String){
        val action = CountryFragmentDirections.actionCountryFragmentToLeagueFeedFragment(countryCode)
        Navigation.findNavController(requireView()).navigate(action)
    }

    fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->
            countries?.let {
                rv_countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer {error->
            error?.let {
                if(it){
                    tv_country_error.visibility = View.VISIBLE
                    rv_countryList.visibility = View.GONE
                }
                else{
                    tv_country_error.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    showProgressBar(getString(R.string.please_wait))
                    rv_countryList.visibility = View.GONE
                    tv_country_error.visibility = View.GONE
                }
                else{
                    hideProgressBar()
                }
            }
        })

    }






}