package com.mycontacts.ui.fragments.local_contacts

import android.database.Cursor
import android.provider.ContactsContract
import com.mycontacts.core.App
import com.mycontacts.data.model.LocalContactsModel
import com.mycontacts.data.repository.Repository
import com.mycontacts.extentions.logLargeString
import com.mycontacts.ui.base.BaseViewModel
import com.mycontacts.utils.SingleLiveEvent

class LocalContactsViewModel(private val mainRepository: Repository) : BaseViewModel(App.instance) {

    val contactList = SingleLiveEvent<List<LocalContactsModel>>()

    override fun subscribe() {
        getContacts()
    }

    private fun getContacts() {

        showProgressDialog()

        val contactsModelArrayList = ArrayList<LocalContactsModel>()

        // this method is use to read contact from users device.
        // on below line we are creating a string variables for
        // our contact id and display name.
        var contactId = ""
        var displayName = ""

        val PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
            ContactsContract.Contacts.LOOKUP_KEY
        )

        // on below line we are calling our content resolver for getting contacts
        val cursor: Cursor? = App.instance.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " ASC"
        )
        // on blow line we are checking the count for our cursor.

        cursor?.let { cursor ->
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    // on below line we are getting the phone number.
                    val hasPhoneNumber: Int = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)).toInt()
                    if (hasPhoneNumber > 0) {
                        // we are checking if the has phone number is > 0
                        // on below line we are getting our contact id and user name for that contact
                        contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                        displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                        // on below line we are calling a content resolver and making a query
                        val phoneCursor: Cursor? = App.instance.contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(contactId),
                            null
                        )
                        // on below line we are moving our cursor to next position.

                        phoneCursor?.let { phoneCursor ->
                            if (phoneCursor.moveToNext()) {
                                // on below line we are getting the phone number for our users and then adding the name along with phone number in array list.
                                val phoneNumber: String = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                                val contact = LocalContactsModel()
                                contact.id = contactId
                                contact.contactName = displayName
                                contact.contactPhoneNumber = phoneNumber

                                contactsModelArrayList.add(contact)
                            }

                            phoneCursor.close()
                        }
                    }
                }
            }
            cursor.close()
        }

        contactsModelArrayList.size.toString().logLargeString("contactsModelArrayList.size")

        contactList.value = contactsModelArrayList

        hideProgressDialog()
    }

}