package com.cruz.matchessimulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cruz.matchessimulator.databinding.ActivityDetailBinding
import com.cruz.matchessimulator.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}