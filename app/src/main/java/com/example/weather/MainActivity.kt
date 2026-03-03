package com.example.weather

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<DataClass>
    lateinit var iconList: Array<Int>
    lateinit var daysList: Array<String>
    lateinit var temperatureList: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fetchWeatherData("Islamabad")
        searchCity()

        iconList = arrayOf(
            R.drawable.mooncloudfastwind,
            R.drawable.mooncloudmidrain,
            R.drawable.suncloudangledrain,
            R.drawable.suncloudmidrain,
            R.drawable.tornado
        )

        daysList = arrayOf(
            "Wed",
            "Thu",
            "Fri",
            "Sat",
            "Sun"
        )

        temperatureList = arrayOf(
            "19°c",
            "20°c",
            "21°c",
            "22°c",
            "18°c"
        )

        recyclerView = findViewById(R.id.forecastRecyclerView)
        dataList = ArrayList<DataClass>()
        getData()
    }
    private fun searchCity() {
        val searchView = binding.search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun fetchWeatherData(cityname: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)
        val response = retrofit.getWeatherData(cityname, "5c8fcffbf2b6a6625f9b8430ebbf550a", "metric")
        response.enqueue(object: Callback<WeatherApp> {
            override fun onResponse(
                call: Call<WeatherApp>,
                response: Response<WeatherApp>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    val temperature = responseBody.main?.temp.toString()
                    val description = responseBody.weather?.get(0)?.description.toString()
                    val maxtemp = responseBody.main?.temp_max.toString()
                    val mintemp = responseBody.main?.temp_min.toString()
                    binding.textView4.text = description
                    binding.textView.text = responseBody.name
                    binding.temp.text = getString(R.string.temperature_celsius, temperature)
                    binding.ht.text = getString(R.string.high_temp, maxtemp)
                    binding.lt.text = getString(R.string.low_temp, mintemp)
                }
            }

            override fun onFailure(
                call: Call<WeatherApp>,
                t: Throwable
            ) {

            }

        })
    }
    private fun getData(){
        for (i in iconList.indices){
            val dataClass = DataClass(iconList[i], daysList[i], temperatureList[i])
            dataList.add(dataClass)
        }
        recyclerView.adapter = Adapter(dataList)
    }
}