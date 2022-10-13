package com.mycompany.footballist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mycompany.footballist.R
import com.mycompany.footballist.databinding.ActivityFootballMainBinding

class FootballMainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFootballMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFootballMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}