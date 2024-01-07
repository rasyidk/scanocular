package com.example.scanocular

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.scanocular.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setupListener()
        setupBottomBar()
        setCurrentFragment(HomeFragment())


    }

    private fun setupListener(){
        with(binding){
            bottomNav.background = null
            bottomNav.setOnItemSelectedListener  {
                when(it.itemId){
                    R.id.home -> setCurrentFragment(HomeFragment())
                    R.id.history -> setCurrentFragment(HistoryFragment())
                }
                true
            }
        }
    }
    @SuppressLint("RestrictedApi")
    private fun setupBottomBar(){
        val navView: BottomNavigationView = findViewById(R.id.bottomNav)
        val navController = findNavController(R.id.flFragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.home, R.id.booked, R.id.history))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val bottomMenuView = navView.getChildAt(0) as BottomNavigationMenuView
        val view = bottomMenuView.getChildAt(1)
        val itemView = view as BottomNavigationItemView
        val viewCustom = LayoutInflater.from(this).inflate(R.layout.scan_button, bottomMenuView, false)
        itemView.addView(viewCustom)
        navView.setOnItemSelectedListener  {
            when(it.itemId){
                R.id.home -> setCurrentFragment(HomeFragment())
                R.id.history -> setCurrentFragment(HistoryFragment())
                R.id.booked ->{

                }
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}