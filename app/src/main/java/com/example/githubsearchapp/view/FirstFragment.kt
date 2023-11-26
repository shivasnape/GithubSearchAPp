package com.example.githubsearchapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchapp.R
import com.example.githubsearchapp.adapter.HomeListRecyclerAdapter
import com.example.githubsearchapp.database.entity.RepoDetailsEntity
import com.example.githubsearchapp.databinding.FragmentFirstBinding
import com.example.githubsearchapp.delegate.OnItemListener
import com.example.githubsearchapp.utils.Constants
import com.example.githubsearchapp.viewmodel.MainViewModel
import com.example.githubsearchapp.viewmodel.common.kodeinViewModel
import com.fmq.ticketmonitoringsystem.utils.isNetworkAvailable
import com.google.gson.Gson
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), KodeinAware, OnItemListener {

    override val kodein by kodein()
    private val viewModel: MainViewModel by kodeinViewModel()

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    lateinit var mRecyclerAdapter: HomeListRecyclerAdapter
    var mItemList: MutableList<RepoDetailsEntity> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        setRecyclerAdapter()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.edtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getData(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })





    }

    private fun getData(query: String?) {
        if (requireContext().isNetworkAvailable()) {
            getRepositoryListFromAPI(keyword = query?:"")
        }
        else {
            getDataFromDB()
        }
    }

    private fun getDataFromDB() {

        viewModel.getDataFromLocalDB().observe(viewLifecycleOwner) {
            if(it!=null){
                Toast.makeText(requireContext(),"Showing Data From DB", Toast.LENGTH_SHORT).show()
                mItemList = it.toMutableList()
                if (::mRecyclerAdapter.isInitialized) {
                    mRecyclerAdapter.setItemList(mItemList)
                }
            }
        }

    }

    private fun setRecyclerAdapter() {

        mRecyclerAdapter = HomeListRecyclerAdapter(requireContext())
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.adapter = mRecyclerAdapter
        // binding.recyclerview.addItemDecoration(SimpleDividerItemDecorator(requireContext()))
          mRecyclerAdapter.setItemListener(this)
    }


    private fun getRepositoryListFromAPI(keyword : String) {

        viewModel.getRepositories(auth = Constants.TOKEN, keyword = keyword)
            .observe(viewLifecycleOwner) {
                Log.e("Tag Response", Gson().toJson(it))

                if (!it.items.isEmpty()) {
                    Toast.makeText(requireContext(),"Showing Data From API", Toast.LENGTH_SHORT).show()
                    it.items.forEach {

                        val data = RepoDetailsEntity()
                        data.name = it.repository.name
                        data.description = it.repository.description
                        data.full_name = it.repository.full_name
                        data.html_url = it.repository.html_url
                        data.avatar_url = it.repository.owner?.avatar_url ?: "-"

                        mItemList.add(data)
                    }

                    if (::mRecyclerAdapter.isInitialized) {
                        mRecyclerAdapter.setItemList(mItemList)
                    }

                    //clear DB
                    viewModel.dropTable()
                    // insert new data in to DB
                    viewModel.insert(it.items)
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(data: Any) {

        if(data!=null) {
            val bun = bundleOf("data" to Gson().toJson(data))
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bun)
        }

    }
}