package com.anu.mediaplayer.ui.theme.mediaplayer

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.anu.mediaplayer.databinding.ActivityFullscreenBinding

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val uri = intent.data!!

        val type = contentResolver.getType(uri)!!.split("/")[0]
        if (type == "audio") {
            binding.placeholderImage.visibility = View.VISIBLE
            val pic = MediaMetadataRetriever()
                .also { it.setDataSource(this, uri) }
                .embeddedPicture

            if (pic != null) {
                BitmapFactory.decodeByteArray(pic, 0, pic.size)
                .also { binding.placeholderImage.setImageBitmap(it) }
            }
        }

        binding.videoView.apply {
            setVideoURI(uri)
            setMediaController(MediaController(this@FullscreenActivity))
            requestFocus(0)
            start()
        }

        // TODO: storage access framework get video (or context resolver..?)
        // or simply make it open videos from other apps (in mainfest)

        // https://drive.google.com/drive/u/0/folders/1zD7_uncVBBswLtbT2ovWr9nkTJTwhuyD
        // https://developer.android.com/guide/topics/providers/document-provider
    }


}