package com.example.robinhsueh.moviercmd

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.robinhsueh.moviercmd.ui.search.SearchFragment
import com.example.robinhsueh.moviercmd.ui.home.HomeFragment
import com.example.robinhsueh.moviercmd.ui.notifications.NotificationsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var manager: FragmentManager
    private lateinit var transaction: FragmentTransaction

    enum class FragmentType{
        home, dashboard, notification
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView(){
        manager = supportFragmentManager
        // set navigation Listener
        nav_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // start from Home
        changeFragmentTo(FragmentType.home)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            R.id.navigation_home -> {
                changeFragmentTo(FragmentType.home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                changeFragmentTo(FragmentType.dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                changeFragmentTo(FragmentType.notification)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun changeFragmentTo(type: FragmentType){
        transaction = manager.beginTransaction()
        when(type){
            FragmentType.home -> {
                val homeFragment = HomeFragment()
                transaction.replace(R.id.base_fragment, homeFragment)
            }
            FragmentType.dashboard -> {
                val dashboardFragment = SearchFragment()
                transaction.replace(R.id.base_fragment, dashboardFragment)
            }
            FragmentType.notification -> {
                val notificationsFragment = NotificationsFragment()
                transaction.replace(R.id.base_fragment, notificationsFragment)
            }
        }
        //transaction.addToBackStack(null)  //按返回時返回上一個fragment
        transaction.commit()
    }

}
