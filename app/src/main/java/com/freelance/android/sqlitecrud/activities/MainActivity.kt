package com.freelance.android.sqlitecrud.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import com.freelance.android.sqlitecrud.R
import com.freelance.android.sqlitecrud.adapter.ContactAdapter
import com.freelance.android.sqlitecrud.data.DatabaseHelper
import com.freelance.android.sqlitecrud.model.Contact
import com.freelance.android.sqlitecrud.utils.Constants.ADD
import com.freelance.android.sqlitecrud.utils.Constants.WHAT
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val LOG_TAG = MainActivity::class.java!!.getName()
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: ContactAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST: onCreate() called...")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()

        fabAdd.setOnClickListener{
            Log.i(LOG_TAG, "TEST: setOnClickListener() called...")

            val i = Intent(this, AddorEditActivity::class.java)
            i.putExtra(WHAT, ADD)
            this.startActivity(i)
        }
     }

    private fun initRecyclerView() {
        Log.i(LOG_TAG, "TEST: initRecyclerView() called...")

        mRecyclerView = this.findViewById(R.id.rv) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        var list: ArrayList<Contact>?
        list = DatabaseHelper.getInstance(this).getAllContacts()
        mAdapter = ContactAdapter(applicationContext, list)
        mRecyclerView!!.adapter = mAdapter
    }
}
