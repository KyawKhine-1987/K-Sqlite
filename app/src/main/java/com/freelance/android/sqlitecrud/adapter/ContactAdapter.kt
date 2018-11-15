package com.freelance.android.sqlitecrud.adapter

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.freelance.android.sqlitecrud.R
import com.freelance.android.sqlitecrud.activities.AddorEditActivity
import com.freelance.android.sqlitecrud.data.DatabaseHelper
import com.freelance.android.sqlitecrud.model.Contact
import com.freelance.android.sqlitecrud.utils.Constants.CONTACT_KEY
import com.freelance.android.sqlitecrud.utils.Constants.UPDATE
import com.freelance.android.sqlitecrud.utils.Constants.WHAT



/**
 * Created by Kyaw Khine on 04/17/2018.
 */
class ContactAdapter(private val context: Context, private var mContactList: ArrayList<Contact>) : RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    private val LOG_TAG = ContactAdapter::class.java!!.getName()

    override fun onBindViewHolder(holder: ContactAdapter.MyViewHolder, position: Int) {
        Log.i(LOG_TAG, "TEST: onBindViewHolder() called...")

        val c: Contact = mContactList[position]
        holder.mName.text = c.name
        holder.mNumber.text = c.number

        holder.ivEdit.setOnClickListener {
            Log.i(LOG_TAG, "TEST: ivEdit setOnClickListener() called...")

            var i = Intent(context, AddorEditActivity::class.java)
            i.putExtra(WHAT, UPDATE)
            i.putExtra(CONTACT_KEY, (mContactList?.get(position)) as Parcelable)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }

        holder.ivDelete.setOnClickListener {
            Log.i(LOG_TAG, "TEST: ivDelete setOnClickListener() called...")

            DatabaseHelper.getInstance(context).deleteContact(mContactList.get(position).id)
            mContactList.removeAt(position)
            notifyDataSetChanged()
            Toast.makeText(context, "Contact deleted.",
                    Toast.LENGTH_SHORT).show()
            /*Toast.makeText(context, getString(R.string.editor_delete_contact_successful),
                    Toast.LENGTH_SHORT).show()*/
        }
    }

    override fun getItemCount(): Int {
        Log.i(LOG_TAG, "TEST: getItemCount() called...")

        return mContactList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.MyViewHolder {
        Log.i(LOG_TAG, "TEST: onCreateViewHolder() called...")

        val v = LayoutInflater.from(parent.context).inflate(R.layout.rows_list, parent, false)
        return MyViewHolder(v)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mName = itemView.findViewById<TextView>(R.id.tvName)
        val mNumber = itemView.findViewById<TextView>(R.id.tvNumber)
        val ivEdit = itemView.findViewById<ImageView>(R.id.ivEdit)
        val ivDelete = itemView.findViewById<ImageView>(R.id.ivDelete)
    }
}
