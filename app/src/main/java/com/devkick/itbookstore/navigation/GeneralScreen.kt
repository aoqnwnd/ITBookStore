package com.devkick.itbookstore.navigation

sealed class GeneralScreen(
	val route: String
) {
	data object DetailBook : GeneralScreen("book/{id}") {
		fun createRoute(isbn13: String) = "book/$isbn13"
	}
}