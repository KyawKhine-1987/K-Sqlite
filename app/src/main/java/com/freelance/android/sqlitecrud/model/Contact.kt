package com.freelance.android.sqlitecrud.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by KyawKhine on 11/14/2018 4:16 PM.
 */


class Contact : Parcelable {

    var id: Int = 0
    var name: String = ""
    var number: String = ""

    constructor() {}

    constructor(mId: Int, mName: String, mNumber: String) {
        this.id = mId
        this.name = mName
        this.number = mNumber
    }

    //override fun writeToParcel(dest: Parcel?, flags: Int) {
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.number);
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Contact(id=$id, name='$name', number='$number')"
    }

    protected constructor(`in`: Parcel) {
        this.id = `in`.readInt()
        this.name = `in`.readString()
        this.number = `in`.readString()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Contact> = object : Parcelable.Creator<Contact> {
            //override fun createFromParcel(source: Parcel?): Contact {
            override fun createFromParcel(source: Parcel): Contact {
                return Contact(source)
            }

            override fun newArray(size: Int): Array<Contact?> {
                return arrayOfNulls(size)
            }

        }
    }
}