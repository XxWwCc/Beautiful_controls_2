package com.qcloud.myapplication.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.qcloud.myapplication.R
import kotlinx.android.synthetic.main.activity_title_test.*
import java.lang.StringBuilder

/**
 * Description：
 * author：Smoker
 * 2018/12/5 16:43
 */
class TitleTestActivity : AppCompatActivity() {

    private var fruitName = ""
    private var fruitImageId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title_test)
        fruitName = intent.getStringExtra(FRUIT_NAME)
        fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        collapsing_toolbar.title = fruitName
        Glide.with(this).load(fruitImageId).into(iv_fruit_image)
        val fruitContent = generateFruitContent(fruitName)
        tv_fruit_content.text = fruitContent

        toolbar.setNavigationOnClickListener{
            finish()
        }
    }

    private fun generateFruitContent(fruitName: String) : String {
        val fruitContent = StringBuilder()
        for (i in 0 until 500) {
            fruitContent.append(fruitName)
        }
        return fruitContent.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        val FRUIT_NAME = "fruit_name"
        val FRUIT_IMAGE_ID = "fruit_image_id"

        fun openActivity(context: Context) {
            context.startActivity(Intent(context, TitleTestActivity::class.java))
        }
    }
}