package com.qcloud.myapplication.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.qcloud.myapplication.enums.NetEnum

/**
 * Description：
 * author：Smoker
 * 2018/12/21 13:57
 */
object TokenUtil {
    var sharedPreferences: SharedPreferences? = null

    @Synchronized
    fun initSharePreferences(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun saveToken(token: String) {
        val editor = sharedPreferences?.edit()
        editor?.putString(NetEnum.TOKEN, token)
        editor?.apply()
    }

    fun clearToken() {
        val editor = sharedPreferences?.edit()
        editor?.putString(NetEnum.TOKEN, "")
        editor?.apply()
    }

    fun getToken() : String? {
        val token = sharedPreferences?.getString(NetEnum.TOKEN, "")
        return token
    }

}