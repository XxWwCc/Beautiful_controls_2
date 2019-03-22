package com.qcloud.myapplication.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Description：sharedPreferences 存储
 * author：Smoker
 * 2019/3/22 11:47
 */
object SharedUtil {
    private var mSharedPreferences: SharedPreferences? = null

    fun initSharedPreference(context: Context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        }
    }

    fun writeString(key: String, value: String?) {
        val editor = mSharedPreferences?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun getString(key: String, defaultValue: String? = null)
        = mSharedPreferences?.getString(key, defaultValue) ?: defaultValue

    fun writeInt(key: String, value: Int) {
        val editor = mSharedPreferences?.edit()
        editor?.putInt(key, value)
        editor?.apply()
    }

    fun getInt(key: String, defaultValue: Int = 0)
            = mSharedPreferences?.getInt(key, defaultValue) ?: defaultValue

    fun writeBoolean(key: String, value: Boolean) {
        val editor = mSharedPreferences?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false)
            = mSharedPreferences?.getBoolean(key, defaultValue) ?: defaultValue

    fun writeFloat(key: String, value: Float) {
        val editor = mSharedPreferences?.edit()
        editor?.putFloat(key, value)
        editor?.apply()
    }

    fun getFloat(key: String, defaultValue: Float = 0F)
            = mSharedPreferences?.getFloat(key, defaultValue) ?: defaultValue
}