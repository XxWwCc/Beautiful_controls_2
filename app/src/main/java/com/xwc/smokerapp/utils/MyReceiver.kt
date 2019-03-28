package com.xwc.smokerapp.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import cn.jpush.android.api.JPushInterface
import com.xwc.smokerapp.ui.weight.MainActivity
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

/**
 * Description：
 * author：Smoker
 * 2019/3/28 10:30
 */
class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            val bundle = intent?.extras
            Timber.e( "[MyReceiver] onReceive - ${intent?.action}, extras: ${printBundle(bundle!!)}")

            if (JPushInterface.ACTION_REGISTRATION_ID == intent.action) {
                val regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID)
                Timber.d( "[MyReceiver] 接收Registration Id : $regId")
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action) {
                Timber.d( "[MyReceiver] 接收到推送下来的自定义消息: ${bundle.getString(JPushInterface.EXTRA_MESSAGE)}")
//                processCustomMessage(context!!, bundle)

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action) {
                Timber.d( "[MyReceiver] 接收到推送下来的通知")
                val notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID)
                Timber.d( "[MyReceiver] 接收到推送下来的通知的ID: $notifactionId")

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action) {
                Timber.d( "[MyReceiver] 用户点击打开了通知")

                //打开自定义的Activity
                val i = Intent(context, MainActivity::class.java)
                i.putExtras(bundle)
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                context?.startActivity(i)

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK == intent.getAction()) {
                Timber.d(

                    "[MyReceiver] 用户收到到RICH PUSH CALLBACK: ${bundle.getString(JPushInterface.EXTRA_EXTRA)}"
                )
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE == intent.action) {
                val connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false)
                Timber.w( "[MyReceiver]${intent.action} connected state change to $connected")
            } else {
                Timber.d( "[MyReceiver] Unhandled intent - ${intent.action}")
            }
        } catch (e: Exception) {

        }
    }

    // 打印所有的 intent extra 数据
	private fun printBundle(bundle: Bundle): String {
        val sb = StringBuilder()
        for (key in bundle.keySet())
        {
            if (key == JPushInterface.EXTRA_NOTIFICATION_ID)
            {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key))
            }
            else if (key == JPushInterface.EXTRA_CONNECTION_CHANGE)
            {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key))
            }
            else if (key == JPushInterface.EXTRA_EXTRA)
            {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA)))
                {
                    Timber.i( "This message has no Extra data")
                    continue
                }
                try
                {
                    val json = JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA))
                    val it = json.keys()

                    while (it.hasNext())
                    {
                        val myKey = it.next()
                        sb.append(
                            "\nkey:" + key + ", value: [" +
                                    myKey + " - " + json.optString(myKey) + "]"
                        )
                    }
                }
                catch (e: JSONException) {
                    Timber.e( "Get message extra JSON error!")
                }

            }
            else
            {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key))
            }
        }
        return sb.toString()
    }


	//send msg to MainActivity
	private fun processCustomMessage(context:Context, bundle: Bundle) {
        /*if (MainActivity.isForeground)
        {
            val message = bundle.getString(JPushInterface.EXTRA_MESSAGE)
            val extras = bundle.getString(JPushInterface.EXTRA_EXTRA)
            val msgIntent = Intent(MainActivity.MESSAGE_RECEIVED_ACTION)
            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message)
            if (!ExampleUtil.isEmpty(extras))
            {
                try
                {
                    val extraJson = JSONObject(extras)
                    if (extraJson.length() > 0)
                    {
                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras)
                    }
                }
                catch (e: JSONException) {

                }

            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent)
        }*/
    }
}