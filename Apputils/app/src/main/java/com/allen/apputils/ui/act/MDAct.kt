package com.allen.apputils.ui.act

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.allen.apputils.R
import com.allen.apputils.ui.adapter.BaseFragmentAdapter
import com.allen.apputils.ui.fragment.FragmentIndex
import com.allen.apputils.ui.fragment.FragmentIndex1
import com.allen.apputils.ui.fragment.FragmentIndex2
import kotlinx.android.synthetic.main.act_md.*


class MDAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_md)
        setupViewPager()
    }


    fun onclick(){

    }

    var mFragments: ArrayList<androidx.fragment.app.Fragment>? = null
    var mTitles = arrayOf("主页", "微博", "相册")
    fun setupViewPager(){
        tabs.addTab(tabs.newTab().setText(mTitles[0]))
        tabs.addTab(tabs.newTab().setText(mTitles[1]))
        tabs.addTab(tabs.newTab().setText(mTitles[2]))

        mFragments = ArrayList<androidx.fragment.app.Fragment>()
        var index = FragmentIndex()
        var index1 = FragmentIndex1()
        var index2 = FragmentIndex2()
        mFragments!!.add(index)
        mFragments!!.add(index1)
        mFragments!!.add(index2)
        val adapter = BaseFragmentAdapter(supportFragmentManager, mFragments!!,mTitles)
        viewpager.adapter = adapter
        tabs.setupWithViewPager(viewpager)

    }


}