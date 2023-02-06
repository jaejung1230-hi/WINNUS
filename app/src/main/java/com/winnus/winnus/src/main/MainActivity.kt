package com.winnus.winnus.src.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.winnus.winnus.R
import com.winnus.winnus.config.BaseActivity
import com.winnus.winnus.databinding.ActivityMainBinding
import com.winnus.winnus.src.main.home.home.HomeFragment
import com.winnus.winnus.src.main.myPage.MyPageFragment
import com.winnus.winnus.src.main.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.winnus.winnus.config.ApplicationClass.Companion.sSharedPreferences
import com.winnus.winnus.src.WineDetail.DetailWineActivity
import com.winnus.winnus.src.login.LoginActivity

class MainActivity: com.winnus.winnus.config.BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent.action == Intent.ACTION_VIEW) {
            if(sSharedPreferences.getString(X_ACCESS_TOKEN,null).isNullOrEmpty()){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }else{
                val boardId = intent.data.toString().split("key1=")
                if(boardId.size >=2){
                    Log.d("key1", boardId[1])
                    val intent = Intent(this, DetailWineActivity::class.java)
                    intent.putExtra("wineId", boardId[1].toInt())
                    startActivity(intent)
                }
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment(), "HomeFragment").commit()
        supportFragmentManager.executePendingTransactions()
        binding.mainBtmNav.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_main_btm_nav_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, HomeFragment(), "HomeFragment")
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_btm_nav_search-> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, SearchFragment(), "SearchFragment")
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    /*
                    R.id.menu_main_btm_nav_article -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ArticleFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    */
                    R.id.menu_main_btm_nav_my_page -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, MyPageFragment(), "MyPageFragment")
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("aaaaa","onMainActivityResult ${requestCode}")
        val fragment = supportFragmentManager?.findFragmentByTag("HomeFragment") as HomeFragment
        fragment.onActivityResult(requestCode, resultCode, data)

    }
}