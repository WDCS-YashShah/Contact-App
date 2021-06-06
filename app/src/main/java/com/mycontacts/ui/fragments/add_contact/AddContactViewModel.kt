package com.mycontacts.ui.fragments.add_contact

import android.app.Activity
import android.content.Intent
import android.provider.ContactsContract
import com.mycontacts.core.App
import com.mycontacts.data.repository.Repository
import com.mycontacts.ui.base.BaseViewModel


class AddContactViewModel(private val mainRepository: Repository) : BaseViewModel(App.instance) {


    override fun subscribe() {

    }

    fun addContact(context: Activity, name:String = "Contact Name",number:String="5555555555") {
        val contactIntent = Intent(ContactsContract.Intents.Insert.ACTION)
        contactIntent.type = ContactsContract.RawContacts.CONTENT_TYPE

        contactIntent
            .putExtra(ContactsContract.Intents.Insert.NAME, name)
            .putExtra(ContactsContract.Intents.Insert.PHONE, number)

        context.startActivityForResult(contactIntent, 1)
    }

}