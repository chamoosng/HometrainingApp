package com.example.healthmyself.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.healthmyself.Adapter.AdapterMainFragment
import com.example.healthmyself.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureBottonNavigation()

        selectFragment(2);
    }
    private fun configureBottonNavigation() {
        xml_main_viewpager.adapter = AdapterMainFragment(supportFragmentManager, 5)
        xml_main_tablayout.setupWithViewPager(xml_main_viewpager)

        val viewBtmNaviMain : View = this.layoutInflater.inflate(R.layout.btm_navigation_main, null, false)
        xml_main_tablayout.getTabAt(0)!!.customView = viewBtmNaviMain.findViewById(R.id.xml_btmnv_btn_timer)
        xml_main_tablayout.getTabAt(1)!!.customView = viewBtmNaviMain.findViewById(R.id.xml_btmnv_btn_calendar)
        xml_main_tablayout.getTabAt(2)!!.customView = viewBtmNaviMain.findViewById(R.id.xml_btmnv_btn_main)
        xml_main_tablayout.getTabAt(3)!!.customView = viewBtmNaviMain.findViewById(R.id.xml_btmnv_btn_video)
        xml_main_tablayout.getTabAt(4)!!.customView = viewBtmNaviMain.findViewById(R.id.xml_btmnv_btn_setting)

        xml_main_viewpager.setOffscreenPageLimit(5);
        
    }
    fun selectFragment(position : Int) {
        xml_main_viewpager.setCurrentItem(position, true)
    }

    override fun onBackPressed() {
        //안드로이드 백버튼 막기
        return
    }
}
