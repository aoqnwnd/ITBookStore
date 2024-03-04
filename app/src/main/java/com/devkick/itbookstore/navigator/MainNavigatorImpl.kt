package com.devkick.itbookstore.navigator

import android.content.Context
import android.content.Intent
import com.devkick.itbookstore.MainActivity
import com.devkick.navigation.MainNavigator
import javax.inject.Inject

class MainNavigatorImpl @Inject constructor() : MainNavigator {
	override fun openMainActivity(context: Context) {
		val intent = Intent(context, MainActivity::class.java)
		context.startActivity(intent)
	}
}