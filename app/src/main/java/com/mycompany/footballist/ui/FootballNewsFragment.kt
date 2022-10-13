package com.mycompany.footballist.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mycompany.footballist.R
import com.mycompany.footballist.adapters.LeagueAdapter
import com.mycompany.footballist.adapters.NewsFeedAdapter
import com.mycompany.footballist.model.news.Article
import com.mycompany.footballist.utils.BaseFragment
import com.mycompany.footballist.viewmodel.CountryViewModel
import com.mycompany.footballist.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_country.*
import kotlinx.android.synthetic.main.fragment_football_news.*


class FootballNewsFragment : BaseFragment() {

    private lateinit var viewModel : NewsViewModel
    private val newsFeedAdapter = NewsFeedAdapter(this, arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_football_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        viewModel.refreshNews()
        rv_newsList.layoutManager = LinearLayoutManager(requireContext())
        rv_newsList.adapter = newsFeedAdapter

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.articleList.observe(viewLifecycleOwner, Observer {articles ->
            articles?.let {
                rv_newsList.visibility = View.VISIBLE
                newsFeedAdapter.updateNewsList(articles)
            }
        })

        viewModel.articleError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if(it){
                    showErrorSnackBar(getString(R.string.network_error_occurred),true)
                }
            }
        })
        viewModel.articleLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    rv_newsList.visibility = View.GONE
                    showProgressBar(getString(R.string.please_wait))
                }
                else{
                    hideProgressBar()
                }
            }
        })

    }

    fun moveToNewsDetails(articleDetails: Article){
        val intent = Intent(requireContext(),NewsDetailsActivity::class.java)
        intent.putExtra("articleDetails",articleDetails)
        startActivity(intent)
    }


}