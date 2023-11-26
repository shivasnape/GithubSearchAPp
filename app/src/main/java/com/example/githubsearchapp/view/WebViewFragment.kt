package com.example.githubsearchapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.githubsearchapp.database.entity.RepoDetailsEntity
import com.example.githubsearchapp.databinding.FragmentSecondBinding
import com.example.githubsearchapp.databinding.FragmentWebviewLayoutBinding

class WebViewFragment : Fragment() {

    private var _binding: FragmentWebviewLayoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var mReceivedURL : String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWebviewLayoutBinding.inflate(inflater, container, false)
        binding.webView.settings.setJavaScriptEnabled(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("data").let {
            mReceivedURL = it
            if(mReceivedURL!=null) {
                loadURL()
            }
        }
    }

    private fun loadURL() {
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url?:"-")
                return true
            }
        }
        binding.webView.loadUrl(mReceivedURL!!)
    }
}