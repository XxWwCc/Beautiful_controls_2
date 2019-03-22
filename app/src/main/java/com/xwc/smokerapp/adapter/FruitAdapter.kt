package com.xwc.smokerapp.adapter

import android.content.Context
import android.support.annotation.NonNull
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.xwc.smokerapp.beans.FruitBean
import com.bumptech.glide.Glide
import com.xwc.smokerapp.R

/**
 * Description：
 * author：Smoker
 * 2018/12/3 17:45
 */
class FruitAdapter(context: Context) : RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

    private val mContext = context
    val mList: ArrayList<FruitBean> = ArrayList()
    var onItemClickListener: AdapterView.OnItemClickListener? = null
    var onHolderClickListener: OnHolderClickListener<FruitBean>? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView = view.findViewById<CardView>(R.id.cardview)
        val fruitImage = view.findViewById<ImageView>(R.id.iv_image)
        val fruitName = view.findViewById<TextView>(R.id.tv_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_of_fruit, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = mList[position]
        holder.fruitName.text = fruit.name
        Glide.with(mContext).load(fruit.image).into(holder.fruitImage)
        holder.cardView.setOnClickListener{
            onItemClickListener?.onItemClick(null, it, position, holder.itemId)
        }
    }

    fun replaceList(list: List<FruitBean>?) {
        mList.clear()
        if (list != null) {
            mList.addAll(list)
        }
        notifyDataSetChanged()
    }

    /**
     * 控件点击监听
     * */
    interface OnHolderClickListener<in T> {
        fun onHolderClick(@NonNull view: View, t: T, position: Int)
    }

}