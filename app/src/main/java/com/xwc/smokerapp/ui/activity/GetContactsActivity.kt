package com.xwc.smokerapp.ui.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Toast
import com.xwc.smokerapp.R
import kotlinx.android.synthetic.main.activity_get_contacts.*
import java.lang.Exception

/**
 * Description：读取电话薄
 * author：Smoker
 * 2019/1/24 09:49
 */
class GetContactsActivity : AppCompatActivity() {

    private var adapter: ArrayAdapter<String>? = null
    private val contactsList = ArrayList<String>()
    private val code = 3    // 请求码

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_contacts)
        initListView()
        btn_get_contacts.setOnClickListener{
            requestPermission()
        }
    }

    private fun initListView() {
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactsList)
        list_contact.adapter = adapter
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, Array<String>(1){Manifest.permission.READ_CONTACTS}, code)
            } else {
                readContacts()
            }
        } else {
            readContacts()
        }
    }

    private fun readContacts() {
        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    contactsList.add(displayName + "\n" + number)
                }
                adapter?.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            code -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts()
                } else {
                    Toast.makeText(this, "请允许打开读取联系人权限", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        fun openActivity(context: Context) {
            context.startActivity(Intent(context, GetContactsActivity::class.java))
        }
    }
}