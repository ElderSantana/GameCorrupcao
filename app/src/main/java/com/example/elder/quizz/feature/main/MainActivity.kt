package com.example.elder.quizz.feature.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import com.example.elder.quizz.R
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.elder.quizz.feature.game.GameActivity


class MainActivity : AppCompatActivity() {

     var viewPager: ViewPager? = null
     var tabLayout: TabLayout? = null
     private val tabIcons = intArrayOf(R.drawable.ic_003_home, R.drawable.ic_001_newspaper, R.drawable.ic_menu)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewPager = findViewById<ViewPager>(R.id.viewpager)

        setupViewPager(viewPager!!)

        tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout!!.setupWithViewPager(viewPager)

        tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout?.setupWithViewPager(viewPager)
        setupTabIcons()
    }

    private fun setupTabIcons() {
        tabLayout?.getTabAt(0)?.setIcon(tabIcons[0])
        tabLayout?.getTabAt(1)?.setIcon(tabIcons[1])
        tabLayout?.getTabAt(2)?.setIcon(tabIcons[2])
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = MainPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "")
        adapter.addFragment(NewsFragment(), "")
        adapter.addFragment(PefilFragment(), "")
        viewPager.adapter = adapter
    }

    fun Jogar(view: View){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }


}


