package com.cruz.matchessimulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cruz.matchessimulator.data.MatchesAPI
import com.cruz.matchessimulator.databinding.ActivityMainBinding
import com.cruz.matchessimulator.domain.Match
import com.google.android.material.snackbar.Snackbar
import kotlin.jvm.javaClass
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var matchesApi: MatchesAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupHttpClient()
        setupMatchesList()
        //setupMatchesRefresh()
        //setupFloatingActionButton()
    }

    private fun setupHttpClient() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://cruzvs.github.io/matches-simulator-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        matchesApi = retrofit.create(MatchesAPI::class.java)
    }

    private fun setupMatchesList() {
        matchesApi.matches?.enqueue(object : Callback<List<Match?>?> {
            override fun onResponse(call: Call<List<Match?>?>, response: Response<List<Match?>?>) {
                if(response.isSuccessful){
                    var matches: List<Match> = response.body() as List<Match>
                    Log.i("Message", "Total de partidas = " + matches.size)
                }else{
                    showErrorMessage()
                }
            }
            override fun onFailure(call: Call<List<Match?>?>, t: Throwable) {
                showErrorMessage()
            }
        })

    }

    private fun setupFloatingActionButton() {
        TODO("Colocar funcionalidade no botão.")
    }

    private fun setupMatchesRefresh() {
        TODO("Refazer partidas com o Swap")
    }

    private fun showErrorMessage(){
        Snackbar.make(binding.btnFloating, R.string.error_api, Snackbar.LENGTH_LONG).show()
    }
}

private fun Any?.enqueue(callback: Callback<List<Match>>) {

}




