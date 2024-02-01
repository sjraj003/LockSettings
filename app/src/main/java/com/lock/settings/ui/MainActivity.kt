package com.lock.settings.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.lock.settings.R


class MainActivity : BaseActivity() {
    private var appBarConfiguration: AppBarConfiguration? = null
    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController?.let {
            appBarConfiguration = AppBarConfiguration.Builder(navController!!.graph).build()
            setupActionBarWithNavController(this, navController!!, appBarConfiguration!!)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navController?.let {
            return (appBarConfiguration?.let {
                NavigationUI.navigateUp(
                    navController!!,
                    it
                )
            } == true
                    || super.onSupportNavigateUp())
        }
        return false
    }
}