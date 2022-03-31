package com.example.disciplines

import android.widget.Toast

fun Toast.applyGravity(gravity: Int, xOffset: Int, yOffset: Int) =
    apply { setGravity(gravity, xOffset, yOffset) }