package com.example.weather_report

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.weather_report.api.Model
import com.example.weather_report.api.Weather
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import java.lang.Exception
import kotlin.coroutines.CoroutineContext



const val BASE_URL = "https://api.openweathermap.org/data/2.5/"



class MainActivity : AppCompatActivity() {
    lateinit var tem: TextView
    lateinit var clouds: TextView
    lateinit var get: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        get = findViewById(R.id.get_weather)
        tem = findViewById(R.id.tem)
        clouds = findViewById(R.id.clouds)


        get.setOnClickListener{
            getCurrentData()
        }
    }


    private  fun getCurrentData(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)



        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.GetWeather().awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    Log.d("Tag", data.base)

                    withContext(Dispatchers.Main) {
                        val temC = data.main.temp - 273
                        tem.text = temC.toLong().toString() + "℃"
                        clouds.text = data.weather[0].description
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        applicationContext,
                        "Seems like something went wrong...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

    }

}






