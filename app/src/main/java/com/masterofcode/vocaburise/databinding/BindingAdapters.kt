package com.masterofcode.vocaburise.databinding

import android.databinding.BindingAdapter
import android.support.v4.widget.SwipeRefreshLayout

@BindingAdapter("onRefresh")
fun onRefresh(swipeRefreshLayout: SwipeRefreshLayout, listener: SwipeRefreshLayout.OnRefreshListener) {
    swipeRefreshLayout.setOnRefreshListener {
        listener.onRefresh()
        swipeRefreshLayout.isRefreshing = false
    }
}