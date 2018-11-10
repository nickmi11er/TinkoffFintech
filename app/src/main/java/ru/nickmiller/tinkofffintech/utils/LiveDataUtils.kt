package ru.nickmiller.tinkofffintech.utils

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations

fun <A, B> LiveData<A>.map(function: (A) -> B): LiveData<B> = Transformations.map(this, function)

fun <A, B> LiveData<A>.switchMap(function: (A) -> LiveData<B>): LiveData<B> = Transformations.switchMap(this, function)
 
