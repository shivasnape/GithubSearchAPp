package com.example.githubsearchapp.adapter

import android.content.Context
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubsearchapp.R
import com.example.githubsearchapp.database.entity.RepoDetailsEntity
import com.example.githubsearchapp.databinding.ItemRowSearchLayoutBinding
import com.example.githubsearchapp.delegate.OnItemListener
import com.google.gson.Gson

class HomeListRecyclerAdapter(val context: Context) : RecyclerView.Adapter<HomeListRecyclerAdapter.MyViewHolder>() {

    private lateinit var mRecyclerBinding: ItemRowSearchLayoutBinding
    private var mItemList: MutableList<RepoDetailsEntity>? = null
    lateinit var mListener : OnItemListener
    lateinit var mContext : Context

    init {
        this.mItemList = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        mRecyclerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_row_search_layout,
            parent, false
        )
        mContext = parent.context
        return MyViewHolder(mRecyclerBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mItemList!![position], position)
    }

    override fun getItemCount(): Int {
        return mItemList!!.size
    }

    fun setItemList(dataList: List<RepoDetailsEntity>) {
        mItemList = dataList.toMutableList()
        notifyDataSetChanged()
    }

    fun filterList(filterList: MutableList<RepoDetailsEntity>) {
        mItemList = filterList
        notifyDataSetChanged()
    }

    fun setSelectedPosition(position: Int) {
        notifyDataSetChanged()
    }


     fun setItemListener(listerner: OnItemListener) {
         this.mListener = listerner
     }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun clearAllItems() {
        mItemList!!.clear()
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private var itemDetailBinding: ItemRowSearchLayoutBinding) :
        RecyclerView.ViewHolder(itemDetailBinding.root) {

        fun bind(data: RepoDetailsEntity, position: Int) {

            if(data.full_name!=null) {
                itemDetailBinding.txtName.setText(data.full_name)
            }

            if(data.description!=null) {
                itemDetailBinding.txtDesc.setText(data.description)
            }
            else {
                itemDetailBinding.txtDesc.setText("-")
            }

            itemDetailBinding.root.setOnClickListener {
                mListener.onItemClicked(data)
            }

           /* if(data.html_url!=null) {
                itemDetailBinding.txtLink.setText(data.html_url)
                itemDetailBinding.txtLink.movementMethod = LinkMovementMethod.getInstance()
            }

            if(data.avatar_url!=null){
                Glide.with(mContext).load(data.avatar_url).into(itemDetailBinding.imgAvatar)
            }
*/
        }
    }


}