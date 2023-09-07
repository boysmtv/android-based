package com.kotlin.learn.core.nav.navigator

import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.kotlin.learn.core.nav.R
import com.kotlin.learn.feature.menu.presentation.ui.MenuFragmentDirections

class ParentNavigator {

    fun fromMenuToGreetings(activity: FragmentActivity) {
        val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navHostFragment.findNavController().navigate(
            MenuFragmentDirections.actionMenuFragmentToGreetingsFragment()
        )
    }

}