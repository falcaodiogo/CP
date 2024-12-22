package ua.diogo.cp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ua.diogo.cp.R
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.authentication.UserData
import ua.diogo.cp.data.retrofit.StationsViewModel
import ua.diogo.cp.ui.components.DropDownStations

@Composable
fun HomeScreen(
    googleAuthUiClient: GoogleAuthUiClient,
    userData: UserData,
    viewModel: StationsViewModel
) {
    Column(
        modifier = Modifier
            .padding(top = 46.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.cplogo),
            contentDescription = "CP logo",
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.padding(46.dp))
        Row {
            DropDownStations(viewModel = viewModel)
        }
    }
}