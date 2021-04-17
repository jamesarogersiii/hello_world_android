package com.example.helloworldandroid.ui

import android.view.View

interface ItemClickListener<T> {
    fun onItemClick(item: T, itemView: View)
}