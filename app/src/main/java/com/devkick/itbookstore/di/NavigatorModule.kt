package com.devkick.itbookstore.di

import com.devkick.itbookstore.navigator.MainNavigatorImpl
import com.devkick.navigation.MainNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigatorModule {

	@Binds
	abstract fun provideMainNavigator(
		navigator: MainNavigatorImpl
	): MainNavigator

}