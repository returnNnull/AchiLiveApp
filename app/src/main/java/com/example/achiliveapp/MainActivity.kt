package com.example.achiliveapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.achiliveapp.auth.ui.AuthUiState
import com.example.achiliveapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mainViewModel = ViewModelProvider(this, MainViewModel())[MainViewModel::class.java]

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = binding!!.bottomNavigation
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, _, arguments ->
            bottomNavigationView.isVisible = arguments?.getBoolean("bottomNavShow") ?: true
        }

        lifecycleScope.launch {

                mainViewModel.authState.collect{
                    if (it is AuthUiState.Logged){
                        showMainView(navController)
                    }
                    if (it is AuthUiState.Empty){
                        showAuthView(navController)
                    }
                }

        }

    }

    private fun showAuthView(navController: NavController) {
        val graph = navController.navInflater.inflate(R.navigation.auth_graph)
        graph.setStartDestination(R.id.signingFragment)
        navController.graph = navController.navInflater.inflate(R.navigation.auth_graph)
    }

    private fun showMainView(navController: NavController) {
        val graph = navController.navInflater.inflate(R.navigation.main_graph)
        graph.setStartDestination(R.id.accountFragment)
        navController.graph = navController.navInflater.inflate(R.navigation.main_graph)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.remoteObservers()
        binding = null
    }
}