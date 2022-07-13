package com.cruz.matchessimulator.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cruz.matchessimulator.R
import com.cruz.matchessimulator.data.MatchesAPI
import com.cruz.matchessimulator.databinding.ActivityMainBinding
import com.cruz.matchessimulator.domain.Match
import com.cruz.matchessimulator.ui.adapter.MatchesAdapter
import com.google.android.material.snackbar.Snackbar
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var matchesApi: MatchesAPI
    private var matchesAdapter = MatchesAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupHttpClient()
        setupMatchesList()
        setupMatchesRefresh()
        setupFloatingActionButton()
    }

    private fun setupHttpClient() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://cruzvs.github.io/matches-simulator-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        matchesApi = retrofit.create(MatchesAPI::class.java)
    }

    private fun setupMatchesList() {
        binding.rwScreen.setHasFixedSize(true)
        binding.rwScreen.layoutManager = LinearLayoutManager(this)
        findMatchesFromAPI()
    }

    private fun setupFloatingActionButton() {
        binding.btnFloating.setOnClickListener{view->
           view.animate().rotationBy(360F).setDuration(1)
               .setListener(object : AnimatorListenerAdapter(){
                   val random = Random
                   override fun onAnimationEnd(animation:Animator?){

                   }
               })
        }
    }
    private fun setupMatchesRefresh() {
        binding.swipeScreen.setOnRefreshListener(this::findMatchesFromAPI)
    }

    private fun showErrorMessage(){
        Snackbar.make(binding.btnFloating, R.string.error_api, Snackbar.LENGTH_LONG).show()
    }
    private fun findMatchesFromAPI(){
        binding.swipeScreen.setRefreshing(true)
        matchesApi.matches?.enqueue(object : Callback<List<Match?>?> {
            override fun onResponse(call: Call<List<Match?>?>, response: Response<List<Match?>?>) {
                if(response.isSuccessful){
                    val matches: List<Match?>? = response.body()
                    matchesAdapter = MatchesAdapter(matches)
                    binding.rwScreen.adapter = matchesAdapter
                }else{
                    showErrorMessage()
                }
                binding.swipeScreen.setRefreshing(false)
            }
            override fun onFailure(call: Call<List<Match?>?>, t: Throwable) {
                showErrorMessage()
            }
        })

    }
}




