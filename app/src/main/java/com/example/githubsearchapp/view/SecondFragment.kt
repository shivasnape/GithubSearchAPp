package com.example.githubsearchapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.githubsearchapp.R
import com.example.githubsearchapp.database.entity.RepoDetailsEntity
import com.example.githubsearchapp.databinding.FragmentSecondBinding
import com.example.githubsearchapp.viewmodel.MainViewModel
import com.example.githubsearchapp.viewmodel.common.kodeinViewModel
import com.google.gson.Gson
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val viewModel: MainViewModel by kodeinViewModel()

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var mReceivedData : RepoDetailsEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("data")?.let {
            mReceivedData = Gson().fromJson(it,RepoDetailsEntity::class.java)
            if(mReceivedData!=null) {
                setRepoDetails()
            }
        }

        binding.txtLink.setOnClickListener {
            val bun = bundleOf("data" to binding.txtLink.text.toString().trim())
            findNavController().navigate(R.id.action_SecondFragment_to_WebView, bun)
        }


    }

    private fun setRepoDetails() {

        mReceivedData?.name.let {
            binding.txtName.setText(it)
        }

        mReceivedData?.description.let {
            binding.txtDesc.setText(it)
        }

        mReceivedData?.html_url.let {
            binding.txtLink.setText(it)
        }

        mReceivedData?.contributors_url?.let {
            loadContributers(it)
        }

        mReceivedData?.avatar_url?.let {
            Glide.with(requireContext()).load(it).into(binding.imgAvatar)
        }


    }

    private fun loadContributers(url: String) {

        viewModel.getContributers(url).observe(viewLifecycleOwner) {
            Log.e("Tag",Gson().toJson(it))
            if(it!=null) {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}