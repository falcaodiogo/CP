package ua.diogo.cp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.diogo.cp.ui.components.ShapeMotion
import ua.diogo.cp.ui.theme.backgroundLight
import ua.diogo.cp.ui.theme.tertiaryLight

@Composable
fun HomeScreen(modifier: Modifier) {
    Surface(
        modifier.fillMaxSize(),
        color = backgroundLight
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundLight),
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
                    OutlinedButton(
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp),
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = tertiaryLight
                        ),
                        border = BorderStroke(1.dp, tertiaryLight)
                    ) {
                        Text("Entrar")
                    }
                    Button(
                        modifier = Modifier.height(50.dp),
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = tertiaryLight
                        ),
                    ) {
                        Text("Entrar com conta")
                    }
                }

            }
        }
    }
}