package com.anu.mediaplayer.ui.theme.mediaplayer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.CodeBoy.MediaFacer.AudioGet
import com.CodeBoy.MediaFacer.MediaFacer
import com.anu.mediaplayer.databinding.MediaListFragmentBinding

class MusicFragment: Fragment() {

    private var _binding: MediaListFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = MediaListFragmentBinding.inflate(inflater, container, false)

        val allAudio = MediaFacer
            .withAudioContex(context)
            .getAllAudioContent(AudioGet.externalContentUri).map {
                MediaItem(it.name, Uri.parse(it.assetFileStringUri), null)
            }

        binding.listView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MediaListAdapter(allAudio.toTypedArray()) {
                val intent = Intent(context, FullscreenActivity::class.java)
                intent.data = it.uri
                startActivity(intent)
            }
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}