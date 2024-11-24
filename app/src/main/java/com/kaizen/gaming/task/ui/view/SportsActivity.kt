package com.kaizen.gaming.task.ui.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.kaizen.gaming.task.ui.view.compose.SportsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportsScreen()
        }
    }
}