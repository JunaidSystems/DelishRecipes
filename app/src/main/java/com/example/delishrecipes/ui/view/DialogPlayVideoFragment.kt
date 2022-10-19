package com.example.delishrecipes.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.delishrecipes.R
import com.example.delishrecipes.databinding.FragmentDialogPlayVideoBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class DialogPlayVideoFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogPlayVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.full_screen_dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogPlayVideoBinding.inflate(inflater)
        dialog?.setCancelable(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                arguments?.getString(ARG_PARAM1)?.let { youTubePlayer.loadVideo(it, 0f) }
            }
        })
    }

    companion object {
        private const val ARG_PARAM1 = "param1"

        fun newInstance(param1: String): DialogPlayVideoFragment {
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            val fragment = DialogPlayVideoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}