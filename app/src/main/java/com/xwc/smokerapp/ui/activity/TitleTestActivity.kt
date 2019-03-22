package com.xwc.smokerapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.xwc.smokerapp.R
import kotlinx.android.synthetic.main.activity_title_test.*
import java.lang.StringBuilder

/**
 * Description：
 * author：Smoker
 * 2018/12/5 16:43
 */
class TitleTestActivity : AppCompatActivity() {

    private var fruitName = ""
    private var fruitImage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title_test)
        fruitName = intent.getStringExtra(FRUIT_NAME)
        fruitImage = intent.getStringExtra(FRUIT_IMAGE)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        collapsing_toolbar.title = fruitName
        Glide.with(this).load(fruitImage).into(iv_fruit_image)
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
        val FRUIT_IMAGE = "fruit_image"

        fun openActivity(context: Context, fruitName: String, fruitImage: String) {
            val intent = Intent(context, TitleTestActivity::class.java)
            intent.putExtra(FRUIT_NAME, fruitName)
            intent.putExtra(FRUIT_IMAGE, fruitImage)
            context.startActivity(intent)
        }
    }
}