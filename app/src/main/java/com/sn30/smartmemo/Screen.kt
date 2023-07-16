package com.sn30.smartmemo

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object MemoScreen : Screen("memo", R.string.memo, Icons.Outlined.Create)
    object SettingScreen : Screen("settings", R.string.settings, Icons.Outlined.Settings)
}


