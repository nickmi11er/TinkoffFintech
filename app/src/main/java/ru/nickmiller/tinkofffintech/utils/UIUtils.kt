package ru.nickmiller.tinkofffintech.utils

import android.app.Activity
import android.view.View

inline fun <reified V : View> Activity.find(id: Int): V = findViewById(id)

inline fun <reified V : View> View.find(id: Int): V = findViewById(id)
 
 
