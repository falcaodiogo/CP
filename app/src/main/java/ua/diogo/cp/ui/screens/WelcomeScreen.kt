package ua.diogo.cp.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ua.diogo.cp.authentication.SignInState
import ua.diogo.cp.ui.components.ShapeMotion
import ua.diogo.cp.ui.theme.backgroundLight
import ua.diogo.cp.ui.theme.primaryLight
import ua.diogo.cp.ui.theme.tertiaryLight

@Composable
fun WelcomeScreen(
    modifier: Modifier,
    navController: NavHostController,
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Surface(
        modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                ShapeMotion()
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 46.dp, horizontal = 32.dp),

                verticalArrangement = Arrangement.spacedBy(42.dp)
            ) {
                Text(
                    text = "Bem vindo à nova app da CP",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 54.sp
                )
                Text(
                    text = "Descobre como o AI chegou à Comboios Portugal!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Thin,
                    lineHeight = 34.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val context = LocalContext.current
                    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cp.pt/")) }

                    OutlinedButton(
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp),
                        onClick = { context.startActivity(intent) },
                        border = BorderStroke(2.dp, primaryLight)
                    ) {
                        Text("Site")
                    }
                    Button(
                        onSignInClick,
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text("Entrar com conta")
                    }
                }

            }
        }
    }
}