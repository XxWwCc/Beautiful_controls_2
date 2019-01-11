package com.qcloud.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.qcloud.myapplication.beans.FruitBean
import com.bumptech.glide.Glide
import com.qcloud.myapplication.R
import com.qcloud.myapplication.activity.TitleTestActivity

/**
 * Description：
 * author：Smoker
 * 2018/12/3 17:45
 */
class FruitAdapter(context: Context, list: List<FruitBean>) : RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

    private val mContext = context
    private val mList = list

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
            val bean = mList[position]
            val intent = Intent(mContext, TitleTestActivity::class.java)
            intent.putExtra(TitleTestActivity.FRUIT_NAME, bean.name)
            intent.putExtra(TitleTestActivity.FRUIT_IMAGE_ID, bean.image)
            mContext.startActivity(intent)
        }
    }

}