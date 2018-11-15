package com.freelance.android.sqlitecrud.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.freelance.android.sqlitecrud.R
import com.freelance.android.sqlitecrud.data.DatabaseHelper
import com.freelance.android.sqlitecrud.model.Contact
import com.freelance.android.sqlitecrud.utils.Constants
import com.freelance.android.sqlitecrud.utils.Constants.CONTACT_KEY
import kotlinx.android.synthetic.main.activity_addoredit.*

class AddorEditActivity : AppCompatActivity() {

    private val LOG_TAG = AddorEditActivity::class.java!!.getName()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST: onCreate() called...")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addoredit)

        if (intent.getStringExtra(Constants.WHAT).equals(Constants.ADD)) {
            btnClick.text = "Save"
        } else {
            btnClick.text = "Update"
            var c: Contact = intent.getParcelableExtra(CONTACT_KEY)
            etName.setText(c.name)
            etPhone.setText(c.number)
        }

        btnClick.setOnClickListener {
            if (intent.getStringExtra(Constants.WHAT).equals(Constants.ADD)) {
                addContact()

                //finish()
            } else {
                updateContact(intent.getParcelableExtra(CONTACT_KEY))

                //finish()
            }
        }
    }

    private fun calledToMainActivity() {
        Log.i(LOG_TAG, "TEST: calledToMainActivity() called...")

        val i = Intent(this, MainActivity::class.java)
        this.startActivity(i)
    }

    private fun addContact() {
        Log.i(LOG_TAG, "TEST: addContact() called...")
        if (validateInput()) {

            val id = DatabaseHelper.getInstance(this).addContact(etName?.text.toString(),
                    etPhone?.text.toString())
            //val result : Int = toIntExact(id) //That "result" is integer datatype which is convert "id" to Long datatype.

            if (id < 0) {
                Toast.makeText(this, getString(R.string.editor_insert_contact_failed),
                        Toast.LENGTH_SHORT).show()
            } else  {
                Toast.makeText(this, getString(R.string.editor_insert_contact_successful),
                        Toast.LENGTH_SHORT).show()
                calledToMainActivity()
            }
        }
    }

    private fun updateContact(c: Contact?) {
        Log.i(LOG_TAG, "TEST: updateContact() called...")

        if (validateInput()) {

            val result  = DatabaseHelper.getInstance(this).updateContact(c?.id, etName.text.toString(), etPhone.text.toString())
            if (result == 0) {
                Toast.makeText(this, getString(R.string.editor_update_contact_failed),
                        Toast.LENGTH_SHORT).show()
            } else  {
                Toast.makeText(this, getString(R.string.editor_update_contact_successful),
                        Toast.LENGTH_SHORT).show()
                calledToMainActivity()
            }
        }
    }

    private fun validateInput(): Boolean {
        Log.i(LOG_TAG, "TEST: validateInput() called...")

        if (etName?.text.toString().trim().equals("") ||
                etPhone?.text.toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.validation), Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }

    fun logMsg(msg: String){
        Log.i(LOG_TAG, "TEST: logMsg() called...")

        Log.d(LOG_TAG, msg)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Log.i(LOG_TAG, "TEST: onOptionsItemSelected() called...")

        if(item != null){
            when(item.itemId){
                android.R.id.home -> finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
