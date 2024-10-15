package com.open.weather.map.presentation

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.open.weather.map.viewModel.OpenWeatherMapViewModel
import java.text.SimpleDateFormat


@Composable
fun WeatherView(modifier: Modifier = Modifier, weatherViewModel: OpenWeatherMapViewModel = hiltViewModel()) {
//    var productViewModel:ProductViewModel =  viewModelFactory {  }
//    val productViewModel = viewModel<ProductViewModel>()
        Column(modifier = Modifier.padding(top = 50.dp, start = 5.dp, end = 5.dp)) {
            Row(modifier = Modifier.padding(bottom = 10.dp)) {
                TextField(
                    value = weatherViewModel.stateObject.searchText,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .width(250.dp),
                    onValueChange = { weatherViewModel.updateSearchText(it) })
                Button(
                    onClick = { weatherViewModel.getSeachResults() },
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(
                        text = "Search"
                    )

                }
            }
            if (weatherViewModel.stateObject.isLoading) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .testTag("SHOW_CIRCULAR"), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }else if (weatherViewModel.stateObject.isGotError) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .testTag("ERROR_MSG"), contentAlignment = Alignment.Center) {
                    Text(text = "Failed to get details.")
                }
            } else {
            if (weatherViewModel.stateObject.cityDetails != null) {
                weatherViewModel.stateObject.cityDetails?.let {
                    val formatter: SimpleDateFormat = SimpleDateFormat("hh:mm:ss")
                    val calendar: Calendar = Calendar.getInstance()
                    it.sys?.let { it1 -> calendar.setTimeInMillis(it1.sunrise) }
                    var sunrise = formatter.format(calendar.time)
                    it.sys?.let { it1 -> calendar.setTimeInMillis(it1.sunset) }
                    var sunset = formatter.format(calendar.time)
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .height(200.dp)
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Row {
                                it.name?.let { it1 -> Text(text = it1) }
                            }

                            Row {
                                AsyncImage(
                                    model = "https://openweathermap.org/img/wn/${it.weather?.get(0)?.icon}@2x.png",
                                    contentDescription = "Translated description of what the image contains"
                                )
                                Column {
                                    it.weather?.get(0)?.let { it1 -> Text(text = it1.main) }
                                    it.weather?.get(0)?.let { it1 -> Text(text = it1.description) }
                                }
                            }
                            Text(text = "Sunrise: $sunrise")
                            Text(text = "Sunset: $sunset")
                        }
                    }
                }
            } else {
//                Text(text = "This city not available for now, please search again")
            }

        }
    }
}