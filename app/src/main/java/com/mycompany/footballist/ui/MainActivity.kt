package com.mycompany.footballist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mycompany.footballist.R
import com.mycompany.footballist.databinding.ActivityMainBinding
import com.mycompany.footballist.model.league.League
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        supportActionBar!!.setBackgroundDrawable(getDrawable(R.drawable.app_gradient_color_background))
        setContentView(view)

        replaceFragment(FootballNewsFragment())
        supportActionBar!!.title = getString(R.string.news)


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_bottom_nav_menu -> replaceFragment(FootballNewsFragment())
                R.id.dictionary_bottom_nav_menu -> replaceFragment(MainDictionaryFragment())
                //R.id.favs_bottom_nav_menu -> replaceFragment(LeagueDetailsFragment())
                else -> {

                }
            }

            when(it.itemId){
                R.id.home_bottom_nav_menu ->supportActionBar!!.title = getString(R.string.news)
                R.id.dictionary_bottom_nav_menu -> supportActionBar!!.title =  getString(R.string.football_dictionary)
                //R.id.favs_bottom_nav_menu -> supportActionBar!!.title = getString(R.string.favourites)
            }


            true
        }

    }



    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView2,fragment)
        fragmentTransaction.commit()
    }



}