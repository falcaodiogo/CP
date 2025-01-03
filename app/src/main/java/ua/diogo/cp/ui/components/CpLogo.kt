package ua.diogo.cp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.diogo.cp.R
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.authentication.UserData

@Composable
fun CpLogo(
    googleAuthUiClient: GoogleAuthUiClient,
    userData: UserData?,
) {
    val username = userData?.username ?: ""
    // get first name
    val username2 = username.split(" ")
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .height(240.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.cplogo),
            contentDescription = "Train icon",
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = "Bem vindo, ${username2[0]}, à app renovada da CP - Comboios Portugal",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}