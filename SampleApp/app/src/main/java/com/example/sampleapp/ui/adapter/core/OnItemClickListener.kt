package com.example.sampleapp.ui.adapter.core

interface OnItemClickListener<T> {
    fun onClick(item: T)
    fun onLongClick(item: T)
}