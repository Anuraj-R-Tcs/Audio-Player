package com.anu.mediaplayer.ui.theme.mediaplayer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.anu.mediaplayer.R
import com.anu.mediaplayer.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MediaListActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        NavigationUI.setupWithNavController(navView, navController)
        navView.setupWithNavController(navController)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Storage access permissions granted", Toast.LENGTH_SHORT).show()
        } else {
            val requestPermissionLauncher =
                registerForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { isGranted: Map<String, Boolean> ->
                    if (isGranted.all { it.value }) {
                        Toast.makeText(this, "Storage access permissions granted", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this, "Storage access permissions not granted", Toast.LENGTH_SHORT).show()

                    }
                }

            requestPermissionLauncher.launch(arrayOf(
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO
                ))
        }

    }

}