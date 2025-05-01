package ua.diogo.cp.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ua.diogo.cp.authentication.UserData
import ua.diogo.cp.notifications.NotificationService

@Composable
fun SettingsScreen(
    userData: UserData?,
    onSignOut: () -> Unit,
    notificationService: NotificationService
) {
    val intent = remember {
        Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/falcaodiogo"))
    }
    val intent2 = remember {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.cp.pt/passageiros/pt/mobile-condicoes/termos-e-condicoes-gerais")
        )
    }
    val context2 = LocalContext.current
    val notifications by remember { mutableStateOf(notificationService.getAllNotifications()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 65.dp, start = 16.dp, end = 16.dp, bottom = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userData?.profilePictureUrl != null && userData.username != null) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 60.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = userData.profilePictureUrl,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = userData.username,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                SettingsButton(label = "Terminar Sessão", icon = Icons.Default.ArrowForward) {
                    onSignOut()
                }
                SettingsButton(label = "Termos e condições", icon = Icons.Default.Info) {
                    context2.startActivity(intent2)
                }
                SettingsButton(label = "Perfil do GitHub", icon = Icons.Default.AccountCircle) {
                    context2.startActivity(intent)
                }
                Text(
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .padding(vertical = 16.dp)
                        .align(Alignment.CenterHorizontally), text = "Notificações passadas:"
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 86.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    for (i in notifications.size - 1 downTo 0) {
                        if (i < notifications.size - 4) {
                            break
                        }
                        val notification = notifications[i]
                        SettingsButton(
                            label = notification.title + " " + notification.content.replace(
                                "Dentro de momentos,",
                                "na"
                            ), icon = Icons.Default.Circle, onSettingClick = {}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsButton(
    label: String,
    icon: ImageVector,
    onSettingClick: () -> Unit
) {
    Button(
        onClick = { onSettingClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}