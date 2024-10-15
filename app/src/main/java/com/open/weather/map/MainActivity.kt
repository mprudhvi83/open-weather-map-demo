package com.open.weather.map

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.open.weather.map.presentation.WeatherView
import com.open.weather.map.ui.theme.OpenWeatherMapTheme
import com.open.weather.map.viewModel.OpenWeatherMapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val schoolViewModel:OpenWeatherMapViewModel by viewModels()
    private var premissionChecked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        schoolViewModel.context = this
        setContent {
            OpenWeatherMapTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherView(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(!Utils.hasAccessFinePermission(this)) {
            if (!premissionChecked) {
                premissionChecked = true
                Utils.requestFineLocationPermission(this)
            }
        } else {
            Utils.getCityName(this) { cityName ->
                schoolViewModel.restoreFromLocal(this, cityName)
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    OpenWeatherMapTheme {
//        Greeting("Android")
//    }
//}