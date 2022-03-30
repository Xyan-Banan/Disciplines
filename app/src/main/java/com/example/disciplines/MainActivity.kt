package com.example.disciplines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.disciplines.ui.CurrentGroup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CurrentGroup.value = "353090490100"
    }
}