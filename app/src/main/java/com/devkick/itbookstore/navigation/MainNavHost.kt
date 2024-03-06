package com.devkick.itbookstore.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devkick.detail_feature.BookDetailScreen
import com.devkick.search_feature.BookSearchListScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = RouteScreen.List.route,
        modifier = modifier
            .padding(paddingValues),
    ) {
        composable(RouteScreen.List.route) {
            BookSearchListScreen(
                modifier = modifier,
                navigateToDetail = { id ->
                    navController.navigate(
                        GeneralScreen.DetailBook.createRoute(id)
                    )
                }
            )
        }

        composable(
            route = GeneralScreen.DetailBook.route,
            arguments = listOf(navArgument("isbn13") { type = NavType.StringType })
        ) {
            BookDetailScreen(
                modifier = modifier.fillMaxSize(),
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}