package com.allen.apputils.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class BaseFragmentAdapter(fm: androidx.fragment.app.FragmentManager, fragment:ArrayList<androidx.fragment.app.Fragment>, title:Array<String>) : androidx.fragment.app.FragmentPagerAdapter(fm) {
    var fragmentList : ArrayList<androidx.fragment.app.Fragment> ?= null
    var titleList : Array<String> ?= null
    init {
        this.fragmentList = fragment
        this.titleList = title
    }
    override fun getItem(i: Int): androidx.fragment.app.Fragment? {
        return fragmentList!![i]
    }

    override fun getCount(): Int {
        return fragmentList!!.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList!![position]
    }
}