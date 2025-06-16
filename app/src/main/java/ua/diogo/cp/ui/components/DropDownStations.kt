package ua.diogo.cp.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import ua.diogo.cp.data.retrofit.StationsViewModel

@Composable
fun DropDownStations(
    viewModel: StationsViewModel,
    navController: NavController
) {
    val stations by viewModel.stations.observeAsState(emptyList())
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var filteredStations by remember { mutableStateOf(stations) }

    LaunchedEffect(Unit) {
        viewModel.fetchStations()
    }

    LaunchedEffect(searchText, stations) {
        filteredStations = if (searchText.isEmpty()) {
            stations.take(4)
        } else {
            stations.filter {
                it.designation.contains(searchText, ignoreCase = true)
            }.take(4)
        }

        if (searchText.isNotEmpty() && filteredStations.isNotEmpty()) {
            delay(800)
            expanded = true
        } else {
            expanded = false
        }
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Próximos comboios",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            modifier = Modifier
                .padding(8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
        ) {
            TextField(
                value = searchText,
                onValueChange = { text ->
                    searchText = text
                },
                placeholder = {
                    Text(
                        text = "Escolha uma estação",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .shadow(2.dp, RoundedCornerShape(24.dp))
                    .background(Color.Transparent),
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.clickable {
                                val station = stations.find {
                                    it.designation.equals(searchText, ignoreCase = true)
                                }
                                station?.let {
                                    navController.navigate("stations/${it.code}")
                                }
                            }
                        )
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    expanded = false
                    val station = stations.find {
                        it.designation.equals(searchText, ignoreCase = true)
                    }
                    station?.let {
                        navController.navigate("stations/${it.code}")
                    }
                }),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Start,
                    lineHeight = 40.sp
                )
            )

            if (expanded) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(300.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp))
                ) {
                    filteredStations.forEach { station ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = station.designation,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            onClick = {
                                searchText = station.designation
                                expanded = false
                                navController.navigate("stations/${station.code}")
                            }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ElevatedButton(
                modifier = Modifier.height(58.dp),
                onClick = {
                    if (searchText.isEmpty()) {
                        Toast.makeText(
                            navController.context,
                            "Escolha uma estação",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val station = stations.find {
                            it.designation.equals(searchText, ignoreCase = true)
                        }
                        if (station != null) {
                            navController.navigate("stations/${station.code}")
                        } else {
                            Toast.makeText(
                                navController.context,
                                "Estação inválida",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                },
                content = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Pesquisar",
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Pesquisar"
                        )
                    }
                }
            )
        }
    }
}