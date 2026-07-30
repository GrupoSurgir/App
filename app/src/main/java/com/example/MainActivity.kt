package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.ui.navigation.SurgirAppContent
import com.example.ui.viewmodel.SurgirViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModelFactory = SurgirViewModel.Factory(applicationContext)
        val viewModel = ViewModelProvider(this, viewModelFactory)[SurgirViewModel::class.java]

        setContent {
            SurgirAppContent(viewModel = viewModel)
        }
    }
}

