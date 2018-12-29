package com.allen.apputils.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class BaseFragmentAdapter(fm: FragmentManager,fragment:ArrayList<Fragment>,title:Array<String>) : FragmentPagerAdapter(fm) {
    var fragmentList : ArrayList<Fragment> ?= null
    var titleList : Array<String> ?= null
    init {
        this.fragmentList = fragment
        this.titleList = title
    }
    override fun getItem(i: Int): Fragment? {
        return fragmentList!![i]
    }

    override fun getCount(): Int {
        return fragmentList!!.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList!![position]
    }
}