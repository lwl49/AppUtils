package com.allen.apputils.ui.act

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.allen.apputils.R
import kotlinx.android.synthetic.main.ac_md_tt.*

/**
 * @user Allen
 * @date 2020/5/12 0012 15:00
 * @purpose
 */
class MDAct2  : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_md_tt)
        recycle.adapter
    }
}