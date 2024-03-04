package com.devkick.itbookstore.navigation

import androidx.annotation.StringRes
import com.itbookstore.resources.R

enum class RouteScreen(
    val route: String,
    @StringRes val title: Int,
    val path: String? = null,
) {
    List("List", R.string.list),
}