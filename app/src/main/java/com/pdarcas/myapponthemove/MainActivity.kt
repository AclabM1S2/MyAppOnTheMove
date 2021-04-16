package com.pdarcas.myapponthemove

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.pdarcas.myapponthemove.ui.login.LoginAdapter


class MainActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var fb: FloatingActionButton? = null
    var google:FloatingActionButton? = null
    var twitter:FloatingActionButton? = null
    var v = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
        fb = findViewById(R.id.facebook)
        google = findViewById<FloatingActionButton>(R.id.fab_google)
        twitter = findViewById<FloatingActionButton>(R.id.fab_twitter)
        tabLayout?.addTab(tabLayout!!.newTab().setText("Login"))
        tabLayout?.addTab(tabLayout!!.newTab().setText("SignUp"))
        tabLayout?.setTabGravity(TabLayout.GRAVITY_FILL)
        val adapter = LoginAdapter(supportFragmentManager, this, tabLayout!!.getTabCount())
        viewPager?.setAdapter(adapter)
        viewPager?.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        tabLayout?.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                viewPager?.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }
   /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }
    fun launchSecondActivity(view: View?) {
        //Log.d(LOG_TAG, "Button clicked!")
        val intent = Intent(this, LoginActivity::class.java)
       // val message: String = mMessageEditText.getText().toString()
       // intent.putExtra(EXTRA_MESSAGE, message)
        startActivity(intent)
    }*/
}
