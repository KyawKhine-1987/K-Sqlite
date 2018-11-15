package com.freelance.android.sqlitecrud.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.freelance.android.sqlitecrud.model.Contact
import com.freelance.android.sqlitecrud.utils.Constants.DATABASE_NAME
import com.freelance.android.sqlitecrud.utils.Constants.DATABASE_VERSION

/**
 * Created by KyawKhine on 11/14/2018 4:08 PM.
 */


class DatabaseHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    private val LOG_TAG = DatabaseHelper::class.java!!.getName()

    private val mTable: String = "Contacts_Table"
    private val mId: String = "id"
    private val mName: String = "name"
    private val mNumber: String = "phone_number" //"phone number" Gave me either error or headache

    private val CreateTable: String = " CREATE TABLE $mTable( $mId INTEGER PRIMARY KEY AUTOINCREMENT," +
            " $mName TEXT NOT NULL," +
            " $mNumber TEXT NOT NULL); "
    private val DropTable: String = " DROP TABLE IF EXISTS $mTable; "

    companion object {

        private var instance: DatabaseHelper? = null
        fun getInstance(ctx: Context): DatabaseHelper {

            if (instance == null) {
                instance = DatabaseHelper(ctx, DATABASE_NAME, null, DATABASE_VERSION)
            }
            return instance!!
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        Log.i(LOG_TAG, "TEST: onCreate() called...")

        p0?.execSQL(CreateTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        Log.i(LOG_TAG, "TEST: onUpgrade() called...")

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        p0?.execSQL(DropTable)
        onCreate(p0)
    }

    fun addContact(name: String?, number: String?): Long {
        Log.i(LOG_TAG, "TEST: addContact() called...")

        //var
        var values = ContentValues()
        values.put(mName, name)
        values.put(mNumber, number)
        return this.writableDatabase.insert(mTable, null, values)
    }

    fun updateContact(id: Int?, name: String?, number: String?): Int {
        Log.i(LOG_TAG, "TEST: updateContact() called...")

        //var
        var values = ContentValues()
        values.put(mName, name)
        values.put(mNumber, number)
        return this.writableDatabase.update(mTable, values, "$mId=?", arrayOf("$id"))
    }

    fun deleteContact(id: Int?) {
        Log.i(LOG_TAG, "TEST: deleteContact() called...")

        this.writableDatabase.delete(mTable, "$mId=?", arrayOf("$id"))
    }

    fun getAllContacts(): ArrayList<Contact> {
        Log.i(LOG_TAG, "TEST: getAllContacts() called...")

        val list: ArrayList<Contact> = ArrayList()
        val c: Cursor = this.writableDatabase.query(mTable, arrayOf(mId, mName, mNumber), null, null, null, null, null)

        if (c.moveToFirst()) {
            do {
                var i = Contact()
                i.id = c.getInt(0)
                i.name = c.getString(1)
                i.number = c.getString(2)
                list.add(i)
            } while (c.moveToNext())
        }
        return list
    }
}