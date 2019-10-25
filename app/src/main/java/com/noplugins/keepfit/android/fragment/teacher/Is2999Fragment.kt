package com.noplugins.keepfit.android.fragment.teacher

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_statistics.*

class Is2999Fragment:BaseFragment() {
    companion object {
        fun newInstance(title: String): Is2999Fragment {
            val fragment = Is2999Fragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }

    var newView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_statistics, container, false)
        }
        return newView
    }



}