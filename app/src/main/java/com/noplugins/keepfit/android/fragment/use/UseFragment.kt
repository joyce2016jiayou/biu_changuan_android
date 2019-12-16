package com.noplugins.keepfit.android.fragment.use

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseFragment

class UseFragment: BaseFragment() {
    companion object {
        fun newInstance(title: String): UseFragment {
            val fragment = UseFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }

    var newView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_use, container, false)
        }
        return newView
    }

}