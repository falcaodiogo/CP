package ua.diogo.cp.navigation

import androidx.compose.ui.graphics.vector.ImageVector

open class Item(val path: String, val title: String, val icon: ImageVector? = null) {
    override fun toString(): String {
        return title
    }
}