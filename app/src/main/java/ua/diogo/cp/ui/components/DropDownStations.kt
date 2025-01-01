package ua.diogo.cp.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ua.diogo.cp.data.retrofit.StationsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownStations(
    viewModel: StationsViewModel,
    navController: NavController
) {
    val stations by viewModel.stations.observeAsState(emptyList())
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var filteredStations by remember { mutableStateOf(stations) }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        viewModel.fetchStations()
    }

    // Update filtered stations whenever searchText or stations change
    LaunchedEffect(searchText, stations) {
        filteredStations = if (searchText.isEmpty()) {
            stations
        } else {
            stations.filter {
                it.designation.contains(searchText, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Próximos comboios",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 8.dp)
                .padding(vertical = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .shadow(3.dp, RoundedCornerShape(8.dp)),
            horizontalArrangement = Arrangement.Center
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .shadow(6.dp, RoundedCornerShape(8.dp)),
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { text ->
                        searchText = text
                        expanded = false
                    },
                    placeholder = {
                        Text(text = "Escolha uma estação")
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedIndicatorColor = MaterialTheme.colorScheme.background,
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .shadow(6.dp, RoundedCornerShape(8.dp))
                        .background(Color.Transparent)
                        .focusRequester(focusRequester),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(onSearch = {
                        expanded = false
                    })
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(300.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
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
                onClick = {
                    if (searchText.isEmpty()) {
                        Toast.makeText(
                            navController.context,
                            "Escolha uma estação",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, content = {
                    Row {
                        Text(text = "Pesquisar")
                        Spacer(modifier = Modifier.padding(start = 12.dp))
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Pesquisar")
                    }
                }
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
