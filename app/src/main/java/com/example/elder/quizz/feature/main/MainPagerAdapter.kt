package com.example.elder.quizz.feature.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


/**
 * Created by elder.santos on 14/08/2017.
 */

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(index: Int): Fragment? {

        when (index) {
            0 ->
                // Top Rated fragment activity
                return TopRatedFragment()
            1 ->
                // Games fragment activity
                return GamesFragment()
            2 ->
                // Movies fragment activity
                return MoviesFragment()
        }

        return null
    }

    override fun getCount(): Int {
        // get item count - equal to number of tabs
        return 3
    }

}

