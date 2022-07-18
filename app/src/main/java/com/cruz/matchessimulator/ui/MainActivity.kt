package com.cruz.matchessimulator.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cruz.matchessimulator.R
import com.cruz.matchessimulator.data.MatchesAPI
import com.cruz.matchessimulator.databinding.ActivityMainBinding
import com.cruz.matchessimulator.domain.Match
import com.cruz.matchessimulator.ui.adapter.MatchesAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var matchesApi: MatchesAPI
    private var matchesAdapter = MatchesAdapter(emptyList())
    private lateinit var firebase: FirebaseApp

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
        binding.rwScreen.adapter = matchesAdapter
        findMatchesFromAPI()
    }

    private fun setupFloatingActionButton() {
        binding.btnFloating.setOnClickListener {
            it.animate().rotationBy(180f).setDuration(500)
                .setListener(object: AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        val random = Random
                        for ( i in 0 until matchesAdapter.itemCount){
                            val homeTeam = matchesAdapter.matches[i].homeTeam
                            val awayTeam = matchesAdapter.matches[i].awayTeam
                            homeTeam.score = random.nextInt(homeTeam.stars )
                            awayTeam.score = random.nextInt(awayTeam.stars )
                            //Avisa o Adapter que teve mudança na tela.
                            matchesAdapter.notifyItemChanged(i)
                        }
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
        binding.swipeScreen.isRefreshing = true
        matchesApi.matches?.enqueue(object : Callback<List<Match?>?> {
            override fun onResponse(call: Call<List<Match?>?>, response: Response<List<Match?>?>) {
                if(response.isSuccessful){
                    val matches: List<Match?>? = response.body()
                    matchesAdapter = MatchesAdapter(matches)
                    binding.rwScreen.adapter = matchesAdapter
                }else{
                    showErrorMessage()
                }
                binding.swipeScreen.isRefreshing = false
            }
            override fun onFailure(call: Call<List<Match?>?>, t: Throwable) {
                showErrorMessage()
            }
        })

    }
}




